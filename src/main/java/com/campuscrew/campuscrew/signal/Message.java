package com.campuscrew.campuscrew.signal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String sender;
    private String type;
    private String receiver;
    private String room;
    private Object candidate;
    private Object sdp;
    private Object allUsers;
}
