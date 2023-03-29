package com.campuscrew.campuscrew.repository.user;


import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.campuscrew.campuscrew.dto.ProjectGroupDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.user.QUser.user;
import static com.querydsl.core.types.dsl.Expressions.list;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public ProfileDto fetchProfile(Long id) {

//        List<ProfileDto> profileDtos = queryFactory.selectFrom(participatedUsers)
//                .innerJoin(project).on(project.user.id.eq(participatedUsers.user.id))
//                .innerJoin(user).on(user.id.eq(participatedUsers.user.id))
//                .distinct()
//                .where(user.id.eq(id))
//                .transform(groupBy(project.projectStatus)
//                        .list(Projections.constructor(ProfileDto.class,
//                        user.id, user.name, user.detailField,
//                                user.selfIntroduction,
//                                user.shortIntroduction,
//                        list(Projections.constructor(ProjectGroupDto.class,
//                                project.projectStatus,
//                                project.title, project.description)))));

        User user1 = queryFactory.select(user)
                .from(user)
                .where(user.id.eq(id))
                .fetchFirst();

        List<ProjectGroupDto> fetch = queryFactory.select(Projections.constructor(
                        ProjectGroupDto.class, project.projectStatus, project.title, project.description))
                .from(participatedUsers)
                .innerJoin(project).on(project.user.id.eq(participatedUsers.user.id))
                .innerJoin(user).on(user.id.eq(participatedUsers.user.id))
                .distinct()
                .where(participatedUsers.user.id.eq(id))
                .fetch();

        for (ProjectGroupDto projectGroupDto : fetch) {
            System.out.println(projectGroupDto);
        }

        ProfileDto profileDto = new ProfileDto(user1.getId(), user1.getName(),
                user1.getDetailField(), user1.getDetailField(), user1.getShortIntroduction(), fetch);


        return profileDto;

    }


}
