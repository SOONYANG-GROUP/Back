package com.campuscrew.campuscrew.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString(of = {"openChatUrl", "voiceChatUrl"})
public class MemberPageDto {
    private String openChatUrl;
    private String voiceChatUrl;
    private List<ParticipatedUserDto> participatedUserDtos;
}
