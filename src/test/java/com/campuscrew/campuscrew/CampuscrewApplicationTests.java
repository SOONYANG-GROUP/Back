package com.campuscrew.campuscrew;

import com.campuscrew.campuscrew.domain.board.ParticipatedUsers;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.domain.board.Recruit;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.ParticipatedUsersRepository;
import com.campuscrew.campuscrew.repository.project.CommentPageDto;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
class CampuscrewApplicationTests {

	@Autowired
	ParticipatedUsersRepository repository;

	@Test
	void contextLoads() {

	}

	@Test
	void date() {

	}

}
