package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectGroupDto {
    private ProjectStatus status;
    private String title;
    private String description;
}
