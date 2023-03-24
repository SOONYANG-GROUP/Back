package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.board.JoinedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JoinedUserRepository extends JpaRepository<JoinedUser, Long> {

    Optional<JoinedUser> findByProjectId(Long projectId);
}
