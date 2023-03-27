package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom{
    @Query("select p from Project p join fetch p.recruits r where p.id = :id")
    Optional<Project> findByIdWithRecruits(@Param("id") Long id);
}
