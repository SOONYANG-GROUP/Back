package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.board.ParticipatedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipatedUsersRepository extends JpaRepository<ParticipatedUsers, Long> {

    Optional<ParticipatedUsers> findByProjectId(Long projectId);
}
