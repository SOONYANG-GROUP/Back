package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.dto.*;
import com.campuscrew.campuscrew.dto.user.AlarmDto;
import com.campuscrew.campuscrew.repository.participateduser.TimeLineRepository;
import com.campuscrew.campuscrew.repository.project.TimeLineListTitleDto;
import com.campuscrew.campuscrew.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final TimeLineRepository timeLineRepository;
    private final UserService userService;

    @PostMapping("/edit")
    public String editUser(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestBody EditForm editForm) {
        String email = userDetails.getUsername();
        userService.editUser(editForm, email);
        return "ok";
    }

    @PostMapping(value = "/join", consumes = "application/json")
    public UserJoinSuccessDto join(@RequestBody
                                       UserJoin userJoin) throws Exception {
        log.info("userJoin = {}", userJoin);
        userService.signUp(userJoin);
        return UserJoinSuccessDto.builder()
                .detailField(userJoin.getDetailField())
                .shortIntroduction(userJoin.getShortIntroduction())
                .selfIntroduction(userJoin.getSelfIntroduction())
                .name(userJoin.getName())
                .email(userJoin.getEmail())
                .build();
    }

    @GetMapping("/profile")
    public ProfileDto getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        ProfileDto profile = userService.getProfile(email);
        return profile;
    }

    @GetMapping("/profile/{userId}/{participatedUsersId}")
    public List<TimeLineListTitleDto> getTimeLineTitleList(@PathVariable Long participatedUsersId) {
        return timeLineRepository.getTimeLineListTitles(participatedUsersId);
    }

    @GetMapping("/profile/{userId}/{participatedUsersId}/{timeLineId}")
    public TimeLineContent getTimeLineContent(@PathVariable Long timeLineId) {
        return timeLineRepository.getTimeLineContent(timeLineId);
    }

    @GetMapping("/profile/{id}")
    public ProfileDto getOtherUserProfile(@PathVariable Long id) {
        ProfileDto profile = userService.getProfile(id);
        return profile;
    }

    @GetMapping("/alarm/setting")
    public Boolean switchAlarm(@AuthenticationPrincipal
                                  UserDetails userDetails) {
        String email = userDetails.getUsername();
        boolean alarm = userService.switchAlarm(email);
        return alarm;
    }

    @GetMapping("/alarm/count")
    public Long getAlarmCount(@AuthenticationPrincipal
                              UserDetails userDetails){
        String email = userDetails.getUsername();
        return userService.getAlarmCount(email);
    }

    @GetMapping("/alarm/list")
    public List<AlarmDto> getAlarms(@AuthenticationPrincipal
                            UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userService.getAlarmList(email);
    }

    @GetMapping("/alarm/confirm/{alarmId}")
    public String confirmAlarm(@PathVariable Long alarmId) {
        userService.confirmAlarm(alarmId);
        return "ok";
    }
}
