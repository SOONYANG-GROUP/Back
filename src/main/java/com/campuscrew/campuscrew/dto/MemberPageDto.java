package com.campuscrew.campuscrew.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"openChatUrl", "voiceChatUrl"})
public class MemberPageDto {
    private Long memberId;
    private String openChatUrl;
    private String voiceChatUrl;
    private String url;
    private String description;
    private List<ParticipatedUserDto> participatedUserDtos;
}
