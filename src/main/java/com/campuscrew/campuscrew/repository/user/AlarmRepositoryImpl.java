package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.domain.user.AlarmStatus;
import com.campuscrew.campuscrew.domain.user.QAlarm;
import com.campuscrew.campuscrew.dto.user.AlarmDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.campuscrew.campuscrew.domain.board.QProject.project;
import static com.campuscrew.campuscrew.domain.user.QAlarm.alarm;
import static com.campuscrew.campuscrew.domain.user.QUser.user;

public class AlarmRepositoryImpl implements AlarmRepositoryCustom{
    private JPAQueryFactory query;

    public AlarmRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<AlarmDto> fetchAlarmList(Long memberId) {
        List<AlarmDto> fetch = query.select(Projections.constructor(AlarmDto.class,
                        alarm.id.as("alarmId"),
                        project.id.as("projectId"),
                        project.title,
                        user.detailField.as("field"),
                        project.createdDateTime.as("createDate")))
                .from(alarm)
                .leftJoin(alarm.user, user)
                .leftJoin(alarm.project, project)
                .where(user.id.eq(memberId), alarm.alarmStatus.eq(AlarmStatus.UNCONFIRMED))
                .fetch();
        return fetch;
    }
}
