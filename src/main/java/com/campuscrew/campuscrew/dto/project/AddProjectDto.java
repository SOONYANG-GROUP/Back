package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Recruit;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class AddProjectDto {
    private String title;
    private String description;
    private String openChatUrl;
    private String voiceChatUrl;
    private String recruitmentDate;
    private List<RecruitUserDto> recruitUserDto;
    private List<ReferenceDto> references;

    public List<Recruit> dtoToRecruit() {
        return this.recruitUserDto.stream()
                .map(Recruit::makeRecruit)
                .collect(Collectors.toList());
    }
}
