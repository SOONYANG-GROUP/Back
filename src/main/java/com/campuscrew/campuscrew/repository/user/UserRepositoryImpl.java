package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.domain.board.QParticipatedUsers;
import com.campuscrew.campuscrew.domain.user.QUser;
import com.campuscrew.campuscrew.dto.ProfileDto;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.user.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ProfileDto fetchProfile(Long memberId) {
//        queryFactory.select(user)
//                .from(user)
//                .leftJoin(participatedUsers).on(participatedUsers.user.id.eq(user.id))
//                .leftJoin(project).on(project.user.id.eq(user.id))
//                .where(user.id.eq(memberId))
//                .groupBy()
//                .fetch();

        return null;
    }
}
