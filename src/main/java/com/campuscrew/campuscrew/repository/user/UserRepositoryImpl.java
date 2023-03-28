package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.domain.board.QParticipatedUsers;
import com.campuscrew.campuscrew.domain.user.QUser;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.user.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ProfileDto fetchProfile(String email) {
        List<ProfileDto> fetch = queryFactory.select(Projections.constructor(ProfileDto.class, user.name, user.detailField))
                .from(user)
                .leftJoin(participatedUsers).on(participatedUsers.user.id.eq(user.id))
                .leftJoin(project).on(project.user.id.eq(user.id))
                .where(user.email.eq(email))
                .fetch();
        for (ProfileDto profileDto : fetch) {
            System.out.println("profileDto = " + profileDto);
        }
        return fetch.stream().findFirst()
                .orElseGet(ProfileDto::new);
    }
}
