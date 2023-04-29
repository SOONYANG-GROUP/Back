package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.controller.JobDto;
import com.campuscrew.campuscrew.domain.board.Job;

import java.util.List;

public interface JobRepositoryCustom {
    List<JobDto> getJobList(Long projectId);
}
