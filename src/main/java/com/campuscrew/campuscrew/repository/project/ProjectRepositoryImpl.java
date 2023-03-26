package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import com.campuscrew.campuscrew.domain.board.QParticipatedUsers;
import com.campuscrew.campuscrew.dto.CountDto;
import com.campuscrew.campuscrew.dto.HomeCardDto;
import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import com.campuscrew.campuscrew.dto.project.ReferenceDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.user.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.board.QRecruit.recruit;
import static com.campuscrew.campuscrew.domain.board.QReference.reference;
import static com.querydsl.core.types.Projections.list;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    };

    @Override
    public ProjectMainDto fetchMainPage(Long id) {
        List<ProjectMainDto> transform = queryFactory
                .select(project)
                .from(project)
                .leftJoin(project.recruits, recruit)
                .leftJoin(project.references, reference)
                .where(project.id.eq(id))
                .transform(groupBy(project.id).list(
                        Projections.constructor(ProjectMainDto.class,
                                list(Projections.bean(RecruitUserDto.class, recruit.field,
                                        recruit.detailField,
                                        recruit.maxRecruit, recruit.currentRecruit)),
                                list(Projections.bean(ReferenceDto.class, reference.url)),
                                project.id, project.createdDateTime, project.title, project.description)));

        return transform.get(0);
    }

    @Override
    public HomeDto fetchCardSortByCreatedDate() {
        List<HomeCardDto> homeCardDtos = queryFactory.select(project, user)
                .from(project)
                .leftJoin(project.user)
                .leftJoin(project.recruits, recruit)
                .orderBy(project.createdDateTime.desc())
                .transform(groupBy(project.id).list(
                        Projections.constructor(HomeCardDto.class,
                                project.id, project.title, project.createdDateTime,
                                project.recruitmentDate,
                                project.projectStatus,
                                list(Projections.constructor(RecruitUserDto.class,
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
                            .orElse(null), projectCount);})
                .collect(Collectors.toList());

        return new HomeDto(homeCardDtos, userCount, collect);
    }
}
