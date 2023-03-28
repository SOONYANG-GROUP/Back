package com.campuscrew.campuscrew.repository.user;


import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.campuscrew.campuscrew.dto.ProjectGroupDto;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
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

        List<ProfileDto> profileDtos = queryFactory.selectFrom(participatedUsers)
                .leftJoin(participatedUsers.project, project)
                .leftJoin(participatedUsers.user, user)
                .where(user.id.eq(id))
                .transform(groupBy(project.projectStatus)
                        .list(Projections.constructor(ProfileDto.class,
                        user.id, user.name, user.detailField,
                        GroupBy.list(Projections.constructor(ProjectGroupDto.class,
                                project.projectStatus,
                                project.title, project.description)))));

        for (ProfileDto profileDto : profileDtos) {
            System.out.println("profileDto = " + profileDto);
            for (ProjectGroupDto projectGroupDto : profileDto.getProjectGroupDtos()) {
                System.out.println(projectGroupDto);
            }
        }

        return profileDtos
                .stream()
                .findFirst()
                .orElseGet(ProfileDto::new);
    }


}
