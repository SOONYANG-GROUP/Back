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
import java.util.List;

@SpringBootTest
class CampuscrewApplicationTests {

	@Autowired
	ParticipatedUsersRepository repository;

	@Test
	void contextLoads() {
		ParticipatedUsers participatedUsers = repository
				.findByUsersIdAndProjectId(4L, 1L)
				.orElse(null);

		Assertions.assertThat(participatedUsers).isNotNull();
	}

	@Test
	void date() {
		LocalDate today = LocalDate.now();
		LocalDate date = LocalDate.parse("2023-03-27");

		System.out.println(today.compareTo(date) >= 0); // start
	}

}
