package com.campuscrew.campuscrew.service;

import com.campuscrew.campuscrew.controller.exception.NotAccessibleAuthenticationException;
import com.campuscrew.campuscrew.controller.exception.RequiredLoginStateException;
import com.campuscrew.campuscrew.domain.board.*;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.ManagerPageDto;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.ParticipatedUsersRepository;
import com.campuscrew.campuscrew.repository.project.CommentPageDto;
import com.campuscrew.campuscrew.repository.project.CommentRepository;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import com.campuscrew.campuscrew.repository.UserRepository;
import com.campuscrew.campuscrew.repository.project.SubCommentRepository;
import com.campuscrew.campuscrew.service.exception.AlreadyAppliedProject;
import com.campuscrew.campuscrew.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ParticipatedUsersRepository participatedUserRepository;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;
    // addProject email을 통해 회원을 조회 하고
    public Project addProject(String email, AddProjectDto addProjectDto) {
        // 1.필요 유저 정보를 조회한다.

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new RequiredLoginStateException("로그인이 필요 합니다."));


        Project project = Project.createProject(findUser, addProjectDto); //
        projectRepository.save(project);

        ParticipatedUsers participatedUsers = ParticipatedUsers.makeParticipatedUserAsManager(findUser, project);
        participatedUserRepository.save(participatedUsers);

        // 2.현재 project에 관한 권한을 가져야 한다.
        // 2-1 project 를 모집하는 유저는 해당 project 게시글에 대한 권한을 통해
        // joinedUser 에 대해서 조회를 해야 할 수 있어야 한다.
        // 2-2 현재 프로젝트에 참여한 member 들은 자신들이 했던 작업 목록을 작성 할 수 있어야 한다.
        // 이러한 이유로 매핑 테이블을 작성
        return project;
    }
    // 회원 가입 되어 있는 유저
    public Comment addComment(String email, Long projectId, String comment) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("가입 되어 있지 않은 유지 입니다."));

        Project project = projectRepository.findById(projectId)
                .orElse(null);

        Comment added = Comment.createComment(findUser, comment, project);
        commentRepository.save(added);
        return added;
    }

    // 1. MainPage 정보를 가져 온다.
    // 2. 필요한 정보

    // 1. 현재 로그인 한 회원 일 경우, 참여 여부도 조회 가능 해야한다.
    public ProjectMainDto getMainPage(Long id) {
        return projectRepository.fetchMainPage(id);
    }

    public HomeDto getHomePage() {
        // 날짜 순서
        HomeDto homeDto = projectRepository.fetchCardSortByCreatedDate();
        return homeDto;
    }
    // project id에 해당하는 모든 댓글을 가져온다.
    // 그에 해당하는 모든 회원 정보를 조회하고 dto로 가져온다.


    // 관리자가 요청에 대해서 거절 했을 때
    // 1. projectId, memberId에 대한 신청 정보를 조회
    // 2. 해당 요청을 거절 하는 것이므로 요청 정보를 삭제
    // 3.
    public void rejectApply(Long projectId, Long memberId) {
        ParticipatedUsers pu = participatedUserRepository.findByUsersIdAndProjectId(memberId, projectId)
                .orElse(null);
        // 1. 해당 participated 요청을 deny
        participatedUserRepository.delete(pu);

        // 1. 이후 해당 회원에게 로그로 알림
    }
    // 관리자가 요청에 대해서 승인 했을 때
    public void acceptApply(Long projectId, Long memberId) {
        ParticipatedUsers participatedUsers = participatedUserRepository.findByUsersIdAndProjectId(memberId, projectId)
                .orElse(null);
        // 1. 이제 승인 되었으니, 해당 프로젝트의 참여 멤버가 됨
        participatedUsers.setStatus(ParticipatedStatus.MEMBER);

        participatedUsers.getRecruit().participateProject();
        // 2. 그에 대한 정보를 로그로 남김

    }

    public CommentPageDto getCommentPage(Long id) {
        return projectRepository.fetchCommentPage(id);
    }

    public void addSubComment(Long commentId, String email, String comment) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("로그인 후 사용 가능 합니다."));

            Comment findComment = commentRepository.findById(commentId)
                .orElse(null);

        SubComment subComment = SubComment.makeSubComment(user, findComment, comment);

        subCommentRepository.save(subComment);

    }
    // 참여 버튼을 누르면 호출되는 api
    // 1. 현재 로그인 되어 있는 user 정보를 조회
    // 2. 지원한 직군에 대한 정보를 조회
    // 3. 프로젝트의 특정 직군에 대해 신청을 넣으면 ready 상태로 저장
    public void applyProject(Long projectId, String email, String detailField) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("로그인 후 사용 가능 합니다."));
        // 1. 프로젝트에 연관 되어 있는 Recruit 까지 모두 조회
        Project project = projectRepository
                .findByIdWithRecruits(projectId)
                .orElse(null);

        if (participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), projectId)
                .isPresent()) {
            throw new AlreadyAppliedProject("이미 지원한 신청인 입니다.");
        };
        // 2. 지원한 Recruit를 선별
        Recruit recruit1 = project.getRecruits()
                .stream().filter(recruit -> detailField.equals(recruit.getDetailField()))
                .findFirst().orElse(null);
        // 3. 해당 Recruit 에 지원


        ParticipatedUsers participatedUsers = ParticipatedUsers
                .makeParticipatedUserAsMReady(user, project, recruit1);

        participatedUserRepository.save(participatedUsers);
    }
    // 1. 현재 유저 정보를 조회하고, 프로젝트 id로 프로젝트를 조회 한다.
    // 2. 현재 유저 정보가 프로젝트의 관리자 이면, ready 상태인 모든 회원에 대한 정보를 볼 수 있어야 한다.
    public ManagerPageDto getManagerPage(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Not login"));

        ParticipatedUsers participatedUsers = participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), id)
                .orElse(null);

        if (participatedUsers.getStatus() != ParticipatedStatus.MANAGER) {
            throw new NotAccessibleAuthenticationException("Manager 만 접근 가능");
        }

        return projectRepository.fetchManagerPage(user.getId(), id);
    }
    //
    public ManagerPageDto getMemberPage(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Not login"));

        ParticipatedUsers participatedUsers = participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), id)
                .orElse(null);

        ParticipatedStatus status = participatedUsers.getStatus();

        if ((status != ParticipatedStatus.MEMBER) && (status != ParticipatedStatus.MANAGER)) {
            throw new NotAccessibleAuthenticationException("Manager 나 Member 만 접근 가능");
        }

        return projectRepository.fetchManagerPage(user.getId(), id);
    }
}
