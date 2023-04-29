package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.controller.JobDto;
import com.campuscrew.campuscrew.domain.board.QJob;
import com.campuscrew.campuscrew.domain.board.QParticipatedUsers;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.campuscrew.campuscrew.domain.board.QJob.job;
import static com.campuscrew.campuscrew.domain.board.QParticipatedUsers.participatedUsers;

public class JobRepositoryImpl implements JobRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public JobRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<JobDto> getJobList(Long projectId) {
        return queryFactory.select(Projections.constructor(JobDto.class, job.id, job.jobTitle,
                        job.jobDescription,
                        job.startJobDate, job.updateJobDate))
                .from(job)
                .where(job.project.id.eq(projectId))
                .fetch();
    }
}
