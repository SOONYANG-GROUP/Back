package com.campuscrew.campuscrew.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProjectDto {
    private String title;
    private String description;
    private String openChatUrl;
    private String voiceChatUrl;
    private List<RequiredUserDto> fields;
    private List<ReferenceDto> referenceDtos;
}
