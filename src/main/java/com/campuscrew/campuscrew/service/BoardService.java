package com.campuscrew.campuscrew.service;

import com.campuscrew.campuscrew.controller.exception.RequiredLoginStateException;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.repository.ProjectRepository;
import com.campuscrew.campuscrew.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    // addProject email을 통해 회원을 조회 하고
    public void addProject(String email, AddProjectDto addProjectDto) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new RequiredLoginStateException("로그인이 필요 합니다."));

    }
}
