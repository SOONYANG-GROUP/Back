package com.campuscrew.campuscrew.repository;

import com.campuscrew.campuscrew.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
