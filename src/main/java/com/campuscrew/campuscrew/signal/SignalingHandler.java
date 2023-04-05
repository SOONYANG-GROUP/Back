package com.campuscrew.campuscrew.signal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SignalingHandler extends TextWebSocketHandler {
    // 어떤 방에 어떤 유저가 있는지 -> [{id : user1}, {id : user2}]
    private final Map<String, List<Map<String, String>>> roomInfo = new HashMap<>();
    // userID 기준 어떤 방에 들어있는지 저장 -> {}
    private final Map<String, String> userInfo = new HashMap<>();

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private static final Integer MAXIMUM = 5;

    private static final String MSG_TYPE_OFFER = "offer";
    // SDP Answer 메시지
    private static final String MSG_TYPE_ANSWER = "answer";
    // 새로운 ICE Candidate 메시지
    private static final String MSG_TYPE_CANDIDATE = "candidate";
    // 방 입장 메시지
    private static final String MSG_TYPE_JOIN = "join_room";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(">>> [ws] 클라이언트 접속 : 세션 {}", session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        try {
            Message message = Utils.getObject(textMessage.getPayload());

            String userId = session.getId();
            String roomID = message.getRoom();

            switch (message.getType()) {
                case MSG_TYPE_OFFER:
                case MSG_TYPE_ANSWER:
                case MSG_TYPE_CANDIDATE:

                    Object candidate = message.getCandidate();
                    Object sdp = message.getSdp();
                    String receiver = message.getReceiver();

                    log.info(">>> [ws] receiver {}", receiver);

                    sessions.values().forEach(s -> {
                        if (s.getId().equals(receiver)) {
                            try {
                                s.sendMessage(new TextMessage(Utils.getString(Message.builder()
                                        .type(message.getType())
                                        .sdp(sdp)
                                        .candidate(candidate)
                                        .sender(userId)
                                        .receiver(receiver)
                                        .build())));
                            } catch (IOException e) {
                                log.info(">>> 에러 발생 offer, candidate, answer 메시지 전달");
//                            throw new RuntimeException(e);
                            }
                        }
                    });
                    break;
                case MSG_TYPE_JOIN:
                    log.info(">>> [ws] {} 가 #{}번 방에 들어감", userId, roomID);

                    if (roomInfo.containsKey(roomID)) {
                        int currentRoomLength = roomInfo.get(roomID).size();

                        if (currentRoomLength == MAXIMUM) {
                            session.sendMessage(new TextMessage(Utils.getString(Message.builder()
                                    .type("room_full")
                                    .sender(userId)
                                    .build())));
                            return;
                        }

                        Map<String, String> userDetail = new HashMap<>();
                        userDetail.put("id", userId);
                        roomInfo.get(roomID).add(userDetail);
                        log.info(">>> [ws] {}번 방의 유저들 {}", roomID, roomInfo.get(roomID));
                    } else {
                        Map<String, String> userDetail = new HashMap<>();
                        userDetail.put("id", userId);
                        List<Map<String, String>> newRoom = new ArrayList<>();
                        newRoom.add(userDetail);
                        roomInfo.put(roomID, newRoom);
                    }

                    sessions.put(userId, session);
                    userInfo.put(userId, roomID);
                    List<Map<String, String>> originRoomUser = new ArrayList<>();
                    for (Map<String, String> userDetail : roomInfo.get(roomID)) {

                        // userUUID 가 본인과 같지 않다면 list 에 추가
                        if (!(userDetail.get("id").equals(userId))) {
                            Map<String, String> userMap = new HashMap<>();
                            userMap.put("id", userDetail.get("id"));
                            originRoomUser.add(userMap);
                        }
                    }
                    log.info(">>> [ws] 본인 {} 을 제외한 #{}번 방의 다른 유저들 {}", userId, roomID, originRoomUser);

                    // all_users 라는 타입으로 메시지 전달
                    session.sendMessage(new TextMessage(Utils.getString(Message.builder()
                            .type("all_users")
                            .allUsers(originRoomUser)
                            .sender(userId).build())));
                    break;
                default:
                    log.info(">>> [ws] 잘못된 메세지 {}", message.getType());

            }
        } catch (IOException e) {
            log.error("에러 발생 {}", e.getMessage());
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(">>> [ws] 클라이언트 접속 해제 : 세션 - {}, 상태 - {}", session, status);

        // 유저 uuid 와 roomID 를 저장
        String userUUID = session.getId(); // 유저 uuid
        String roomId = userInfo.get(userUUID); // roomId

        // 연결이 종료되면 sessions 와 userInfo 에서 해당 유저 삭제
        sessions.remove(userUUID);
        userInfo.remove(userUUID);

        // roomInfo = { 방번호 : [ { id : userUUID1 }, { id: userUUID2 }, …], 방번호 : [ { id : userUUID3 }, { id: userUUID4 }, …], ... }
        // 해당하는 방의 value 인 user list 의 element 의 value 가 현재 userUUID 와 같다면 roomInfo 에서 remove
        List<Map<String, String>> removed = new ArrayList<>();
        roomInfo.get(roomId).forEach(s -> {
            try {
                if(s.containsValue(userUUID)) {
                    removed.add(s);
                }
            }
            catch (Exception e) {
                log.info(">>> 에러 발생 : if문 생성 실패 {}", e.getMessage());
            }
        });
        roomInfo.get(roomId).removeAll(removed);

        // 본인을 제외한 모든 유저에게 user_exit 라는 타입으로 메시지 전달
        sessions.values().forEach(s -> {
            try {
                if(!(s.getId().equals(userUUID))) {
                    s.sendMessage(new TextMessage(Utils.getString(Message.builder()
                            .type("user_exit")
                            .sender(userUUID).build())));
                }
            }
            catch (Exception e) {
                log.info(">>> 에러 발생 : user_exit 메시지 전달 실패 {}", e.getMessage());
            }
        });

        log.info(">>> [ws] #{}번 방에서 {} 삭제 완료", roomId, userUUID);
        log.info(">>> [ws] #{}번 방에 남은 유저 {}", roomId, roomInfo.get(roomId));
    }

    // 소켓 통신 에러
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.info(">>> 에러 발생 : 소켓 통신 에러 {}", exception.getMessage());
    }
}
