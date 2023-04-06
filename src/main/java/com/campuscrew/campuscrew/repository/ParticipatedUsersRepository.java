package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.board.ParticipatedStatus;
import com.campuscrew.campuscrew.domain.board.ParticipatedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipatedUsersRepository extends JpaRepository<ParticipatedUsers, Long> {

    Optional<ParticipatedUsers> findByProjectId(Long projectId);

    @Query("select pu from ParticipatedUsers pu left join fetch pu.recruit r1 where pu.user.id = :usersId and pu.project.id = :projectId")
    Optional<ParticipatedUsers> findByUsersIdAndProjectId(@Param("usersId") Long usersId, @Param("projectId") Long projectId);

    @Query("select pu from ParticipatedUsers pu where pu.project.id = :id and pu.project.projectStatus = :status")
    List<ParticipatedUsers> findParticipatedUsersByProjectIdAnAndStatus(@Param("id") Long id, @Param("status") ParticipatedStatus status);
}
