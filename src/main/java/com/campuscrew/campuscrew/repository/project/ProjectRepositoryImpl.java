package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.QProject;
import com.campuscrew.campuscrew.domain.board.QRecruit;
import com.campuscrew.campuscrew.domain.board.QReference;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import com.campuscrew.campuscrew.dto.project.ReferenceDto;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import static com.querydsl.core.group.GroupBy.groupBy;


import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.board.QRecruit.recruit;
import static com.campuscrew.campuscrew.domain.board.QReference.reference;
import static com.querydsl.core.types.Projections.list;
import static java.util.Collections.list;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ProjectMainDto fetchMainPage(Long id) {
        ProjectMainDto projectMainDto = queryFactory
                .select(project)
                .join(project.recruits, recruit)
                .join(project.references, reference)
                .where(project.id.eq(id))
                .transform(groupBy(project.id).as(
                        Projections.constructor(ProjectMainDto.class,
                                project.id, project.createdDateTime.as("createDate"),
                                project.title, project.description
                                , list(Projections.constructor(RecruitUserDto.class,
                                                recruit.field, recruit.detailField,
                                                recruit.maxRecruit, recruit.currentRecruit)),
                                        list(Projections.constructor(ReferenceDto.class, reference.url))
                        ))).get(id);
        return projectMainDto;
    }
}
