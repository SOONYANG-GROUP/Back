package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.ManagerPageDto;
import com.campuscrew.campuscrew.dto.MemberPageDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;

import java.util.List;

public interface ProjectRepositoryCustom {
    ProjectMainDto fetchMainPage(Long id);

    HomeDto fetchCardSortByCreatedDate();

    CommentPageDto fetchCommentPage(Long id);

    List<SubCommentDto> fetchSubComment(Long commentId);

    ManagerPageDto fetchManagerPage(Long userId, Long projectId);

    MemberPageDto fetchMemberPage(Long projectId, Long id);
}
