package com.campuscrew.campuscrew;

import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampuscrewApplicationTests {


	@Autowired
	ProjectRepository projectRepository;

	@Test
	void test() {
		ProjectMainDto projectMainDto = projectRepository.fetchMainPage(1L);
	}

	@Test
	void contextLoads() {

	}

}
