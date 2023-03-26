package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.HomeCardDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;

import java.util.List;

public interface ProjectRepositoryCustom {
    ProjectMainDto fetchMainPage(Long id);

    List<HomeCardDto> fetchCardSortByCreatedDate();
}
