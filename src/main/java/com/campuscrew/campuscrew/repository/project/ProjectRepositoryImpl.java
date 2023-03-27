package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.ParticipatedStatus;
import com.campuscrew.campuscrew.domain.board.ProjectStatus;

import com.campuscrew.campuscrew.domain.board.QParticipatedUsers;
import com.campuscrew.campuscrew.domain.board.QReference;
import com.campuscrew.campuscrew.dto.*;
import com.campuscrew.campuscrew.dto.project.*;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.campuscrew.campuscrew.domain.board.QComment.comment1;
import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.board.QRecruit.recruit;
import static com.campuscrew.campuscrew.domain.board.QReference.reference;
import static com.campuscrew.campuscrew.domain.board.QSubComment.subComment1;
import static com.campuscrew.campuscrew.domain.user.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;
import static java.util.stream.Collectors.toList;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public ProjectMainDto fetchMainPage(Long id) {
        List<ProjectMainDto> transform = queryFactory
                .select(project)
                .from(project)
                .leftJoin(recruit).on(recruit.project.id.eq(project.id))
                .leftJoin(reference).on(reference.project.id.eq(project.id))
                .where(project.id.eq(id))
                .transform(groupBy(project.id).list(Projections.constructor(ProjectMainDto.class,
                        GroupBy.list(Projections.constructor(RecruitUserDto.class,
                                recruit.field, recruit.detailField, recruit.maxRecruit, recruit.currentRecruit)),
                        GroupBy.list(Projections.constructor(ReferenceDto.class, reference.url)),
                        project.id, project.createdDateTime, project.title, project.description)));
        for (ProjectMainDto projectMainDto : transform) {
            System.out.println("projectMainDto = " + projectMainDto);
        }
        return transform.stream()
                .filter(projectMainDto -> projectMainDto.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public HomeDto fetchCardSortByCreatedDate() {
        List<HomeCardDto> homeCardDtos = queryFactory.select(project, user)
                .from(project)
                .leftJoin(recruit).on(recruit.project.id.eq(project.id))
                .orderBy(project.createdDateTime.desc())
                .transform(groupBy(project.id).list(
                        Projections.constructor(HomeCardDto.class,
                                project.id, project.title, project.createdDateTime,
                                project.recruitmentDate,
                                project.projectStatus,
                                GroupBy.list(Projections.constructor(RecruitUserDto.class,
                                        recruit.field,
                                        recruit.detailField,
                                        recruit.maxRecruit, recruit.currentRecruit)))));
        Long userCount = queryFactory
                .select(user.count())
                .from(user)
                .fetchOne();

        List<CountDto> collect = queryFactory.select(project.projectStatus, project.count())
                .from(project)
                .groupBy(project.projectStatus)
                .fetch()
                .stream()
                .map(tuple -> {
                    ProjectStatus projectStatus = tuple.get(project.projectStatus);
                    Long projectCount = tuple.get(project.count());
                    return new CountDto(Optional.of(projectStatus.name())
                            .orElse(null), projectCount);
                })
                .collect(toList());

        return new HomeDto(homeCardDtos, userCount, collect);
    }

    @Override
    public CommentPageDto fetchCommentPage(Long id) {
        List<CommentPageDto> createDate = queryFactory.select(comment1)
                .from(comment1)
                .leftJoin(comment1.user, user)
                .leftJoin(comment1.project, project)
                .where(comment1.project.id.eq(id))
                .transform(groupBy(project.id).list(
                        Projections.constructor(CommentPageDto.class,
                                GroupBy.list(Projections.constructor(CommentDto.class,
                                        comment1.id,
                                        comment1.subCommentCount,
                                        user.name,
                                        comment1.createTime.as("createDate"),
                                        comment1.comment)))));
        return createDate.stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<SubCommentDto> fetchSubComment(Long commentId) {
        return queryFactory.selectFrom(subComment1)
                .leftJoin(subComment1.user, user)
                .leftJoin(subComment1.comment, comment1)
                .where(comment1.id.eq(commentId))
                .transform(groupBy(comment1.id)
                        .list(Projections.constructor(SubCommentDto.class,
                        subComment1.id.as("subCommentId"),
                                user.name, subComment1.createTime.as("createDate"),
                                subComment1.subComment)));
    }
    // 1. 회원 상태가 READY인 모든 회원 정보를 조회 할 수 있어야 한다.
    @Override
    public ManagerPageDto fetchManagerPage(Long userId, Long projectId) {
        List<ManagerPageDto> transform = queryFactory.select(participatedUsers)
                .from(participatedUsers)
                .leftJoin(participatedUsers.user, user)
                .leftJoin(participatedUsers.project, project)
                .where(project.id.eq(projectId), participatedUsers.status.eq(ParticipatedStatus.READY))
                .transform(groupBy(participatedUsers.id).list(
                        Projections.constructor(ManagerPageDto.class,
                                GroupBy.list(
                                        Projections.constructor(AppliedUserDto.class, user.id, user.name)
                                ))));
        return transform.stream()
                .findFirst()
                .orElse(null);
    }


}