package com.campuscrew.campuscrew.service;

import com.campuscrew.campuscrew.controller.exception.RequiredLoginStateException;
import com.campuscrew.campuscrew.domain.board.ParticipatedUsers;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.HomeCardDto;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.ParticipatedUsersRepository;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import com.campuscrew.campuscrew.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ParticipatedUsersRepository participatedUserRepository;

    // addProject email을 통해 회원을 조회 하고
    public Project addProject(String email, AddProjectDto addProjectDto) {
        // 1.필요 유저 정보를 조회한다.

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new RequiredLoginStateException("로그인이 필요 합니다."));


        Project project = Project.createProject(addProjectDto); //
        projectRepository.save(project);

        ParticipatedUsers participatedUsers = ParticipatedUsers.createAsManager(findUser, project);
        participatedUserRepository.saveAndFlush(participatedUsers);
        // 2.현재 project에 관한 권한을 가져야 한다.
        // 2-1 project 를 모집하는 유저는 해당 project 게시글에 대한 권한을 통해
        // joinedUser 에 대해서 조회를 해야 할 수 있어야 한다.
        // 2-2 현재 프로젝트에 참여한 member 들은 자신들이 했던 작업 목록을 작성 할 수 있어야 한다.
        // 이러한 이유로 매핑 테이블을 작성
        return project;
    }

    // 1. MainPage 정보를 가져 온다.
    // 2. 필요한 정보

    public ProjectMainDto getMainPage(Long id) {
        return projectRepository.fetchMainPage(id);
    }

    public List<HomeCardDto> getHomePage() {
        // 날짜 순서
        List<HomeCardDto> homeCardDtos = projectRepository.fetchCardSortByCreatedDate();
        return null;
    }
}
