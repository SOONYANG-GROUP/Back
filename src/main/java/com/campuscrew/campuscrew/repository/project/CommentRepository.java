package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
