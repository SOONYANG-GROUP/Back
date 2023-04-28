package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectGroupDto {
    private Long id;
    private Long participateUsersId;
    private ProjectStatus status;
    private String title;
    private String description;
}
