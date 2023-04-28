package com.campuscrew.campuscrew;

import com.campuscrew.campuscrew.repository.participateduser.ParticipatedUsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
