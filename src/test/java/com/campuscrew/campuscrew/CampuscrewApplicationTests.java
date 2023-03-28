package com.campuscrew.campuscrew;

import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.domain.board.Recruit;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.project.CommentPageDto;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class CampuscrewApplicationTests {



	@Test
	void contextLoads() {

	}

	@Test
	void date() {
		LocalDate today = LocalDate.now();
		LocalDate date = LocalDate.parse("2023-03-27");

		System.out.println(today.compareTo(date) >= 0); // start
	}

}
