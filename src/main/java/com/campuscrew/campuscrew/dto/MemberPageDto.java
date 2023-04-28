package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"openChatUrl", "voiceChatUrl"})
public class MemberPageDto implements MemberPages{
    private ProjectStatus projectStatus;
    private String openChatUrl;
    private String voiceChatUrl;
    private List<ParticipatedUserDto> participatedUserDtos;

}
