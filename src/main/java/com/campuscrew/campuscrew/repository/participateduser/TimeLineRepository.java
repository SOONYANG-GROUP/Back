package com.campuscrew.campuscrew.repository.participateduser;

import com.campuscrew.campuscrew.domain.board.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLineRepository extends JpaRepository<TimeLine, Long>, TimeLineRepositoryCustom {
}
