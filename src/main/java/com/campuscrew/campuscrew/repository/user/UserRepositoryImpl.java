package com.campuscrew.campuscrew.repository.user;


import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.campuscrew.campuscrew.dto.ProjectGroupDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.user.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public ProfileDto fetchProfile(Long id) {

        User user1 = queryFactory.select(user)
                .from(user)
                .where(user.id.eq(id))
                .fetchFirst();

        List<ProjectGroupDto> fetch = queryFactory.select(Projections.constructor(ProjectGroupDto.class,
                        project.id,
                        participatedUsers.id,
                        project.projectStatus,
                        project.title, project.description))
                .from(participatedUsers)
                .leftJoin(participatedUsers.project, project)
                .leftJoin(participatedUsers.user, user)
                .where(user.id.eq(id))
                .fetch();

        for (ProjectGroupDto projectGroupDto : fetch) {
            System.out.println(projectGroupDto);
        }


        ProfileDto profileDto = new ProfileDto(
                user1.getId(),
                user1.getEmail(),
                user1.getName(),
                user1.getDetailField(), user1.getDetailField(),
                user1.getShortIntroduction(), fetch);

        return profileDto;

    }


}
