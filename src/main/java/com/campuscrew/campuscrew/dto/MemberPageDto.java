package com.campuscrew.campuscrew.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MemberPageDto {
    private String openChatUrl;
    private String voiceChatUrl;
    private List<ParticipatedUserDto> participatedUserDtos;
}
