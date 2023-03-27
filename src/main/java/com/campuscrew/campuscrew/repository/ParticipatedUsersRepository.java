package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.board.ParticipatedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParticipatedUsersRepository extends JpaRepository<ParticipatedUsers, Long> {

    Optional<ParticipatedUsers> findByProjectId(Long projectId);

    @Query("select pu from ParticipatedUsers pu " +
            "where pu.user.id = :usersId and pu.project.id = :projectId")
    Optional<ParticipatedUsers> findByUsersIdAndProjectId(Long usersId, Long projectId);
}