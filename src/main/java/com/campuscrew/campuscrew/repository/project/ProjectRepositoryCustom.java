package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;

public interface ProjectRepositoryCustom {
    ProjectMainDto fetchMainPage(Long id);

    HomeDto fetchCardSortByCreatedDate();

    CommentPageDto fetchCommentPage(Long id);
}
