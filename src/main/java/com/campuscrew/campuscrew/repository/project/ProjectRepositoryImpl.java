package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.HomeCardDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import com.campuscrew.campuscrew.dto.project.ReferenceDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
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
                                list(Projections.constructor(RecruitUserDto.class, recruit.field,
                                        recruit.detailField,
                                        recruit.maxRecruit, recruit.currentRecruit)),
                                list(Projections.constructor(ReferenceDto.class, reference.url)),
                                project.id, project.createdDateTime, project.title, project.description)));
        for (ProjectMainDto projectMainDto : transform) {
            System.out.println(projectMainDto);
        }
        return transform.get(0);
    }

    @Override
    public List<HomeCardDto> fetchCardSortByCreatedDate() {
        List<HomeCardDto> homeCardDtos = queryFactory.select(project, user)
                .from(project)
                .leftJoin(project.recruits, recruit)
                .orderBy(project.createdDateTime.desc())
                .offset(1)
                .limit(6)
                .transform(groupBy(project.id).list(
                        Projections.constructor(HomeCardDto.class,
                                project.id, project.title,
                                project.recruitmentDate,
                                project.status, list(Projections.constructor(RecruitUserDto.class, recruit.field,
                                        recruit.detailField,
                                        recruit.maxRecruit, recruit.currentRecruit)
                                )
                        )));
        Long userCount = queryFactory.select(user.count())
                .from(user)
                .fetchOne();
        List<Tuple> fetch = queryFactory.select(project.status, project.count())
                .from(project)
                .groupBy(project.status)
                .fetch();
        for (Tuple tuple : fetch) {
            System.out.println("tuple = " + tuple);
        }
        return homeCardDtos;
    }
}
