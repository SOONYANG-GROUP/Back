package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom {
}
