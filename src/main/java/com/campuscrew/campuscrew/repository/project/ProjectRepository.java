package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom{
}
