package com.campuscrew.campuscrew.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"openChatUrl", "voiceChatUrl"})
public class MemberPageDto {
    private String openChatUrl;
    private String voiceChatUrl;
    private List<ParticipatedUserDto> participatedUserDtos;
}
