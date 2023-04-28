package com.campuscrew.campuscrew.repository.participateduser;

import com.campuscrew.campuscrew.domain.board.QTimeLine;
import com.campuscrew.campuscrew.repository.project.TimeLineListTitleDto;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;
import static com.campuscrew.campuscrew.domain.board.QTimeLine.timeLine;
import static com.querydsl.core.group.GroupBy.groupBy;

public class TimeLineRepositoryImpl implements TimeLineRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public TimeLineRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    public List<TimeLineListTitleDto> getTimeLineListTitles(Long participatedUserId) {
        return queryFactory.select(Projections.constructor(TimeLineListTitleDto.class, timeLine.id, timeLine.title))
                .from(timeLine)
                .leftJoin(timeLine.participatedUsers, participatedUsers)
                .where(participatedUsers.id.eq(participatedUserId))
                .orderBy(timeLine.createJobTime.asc())
                .fetch();
    }
}
