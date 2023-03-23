package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.board.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
