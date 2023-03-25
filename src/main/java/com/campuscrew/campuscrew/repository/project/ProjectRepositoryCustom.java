package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.project.ProjectMainDto;

public interface ProjectRepositoryCustom {
    ProjectMainDto fetchMainPage(Long id);

}
