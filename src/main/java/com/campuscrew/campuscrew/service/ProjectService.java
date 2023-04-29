package com.campuscrew.campuscrew.service;

import com.campuscrew.campuscrew.controller.JobDto;
import com.campuscrew.campuscrew.controller.exception.NotAccessibleAuthenticationException;
import com.campuscrew.campuscrew.controller.exception.RequiredLoginStateException;
import com.campuscrew.campuscrew.domain.board.*;
import com.campuscrew.campuscrew.domain.user.Alarm;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.*;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.participateduser.ParticipatedUsersRepository;
import com.campuscrew.campuscrew.repository.participateduser.TimeLineRepository;
import com.campuscrew.campuscrew.repository.project.*;
import com.campuscrew.campuscrew.repository.user.AlarmRepository;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import com.campuscrew.campuscrew.service.exception.AlreadyAppliedProject;
import com.campuscrew.campuscrew.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.campuscrew.campuscrew.domain.board.ParticipatedStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectService {
    private final TimeLineRepository timeLineRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ParticipatedUsersRepository participatedUserRepository;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;
    private final AlarmRepository alarmRepository;
    private final JobRepository jobRepository;

    public Project addProject(String email, AddProjectDto addProjectDto) {

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new RequiredLoginStateException("로그인이 필요 합니다."));


        Project project = Project.createProject(findUser, addProjectDto); //
        projectRepository.save(project);

        ParticipatedUsers participatedUsers = ParticipatedUsers.makeParticipatedUserAsManager(findUser, project);
        participatedUserRepository.save(participatedUsers);


        userRepository.findAll()
                .stream()
                .filter(User::getAcceptAlarm)
                .filter(user -> project
                        .getRecruits()
                        .stream()
                        .map(Recruit::getField)
                        .anyMatch(field ->{
                            System.out.println(field + ", user Detail Field " + user.getDetailField());
                            return user.getDetailField().equals(field);
                        }))
                .forEach(user -> {
                    Alarm alarm = Alarm.createAlarm(project, user);
                    alarmRepository.save(alarm);
                });
        return project;
    }

    public Comment addComment(String email, Long projectId, String comment) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("가입 되어 있지 않은 유지 입니다."));

        Project project = projectRepository.findById(projectId)
                .orElse(null);

        Comment added = Comment.createComment(findUser, comment, project);
        commentRepository.save(added);
        return added;
    }


    public ProjectMainDto getMainPage(Long id) {

        return projectRepository.fetchMainPage(id);
    }

    public HomeDto getHomePage() {
        return projectRepository.fetchCardSortByCreatedDate();
    }

    public void rejectApply(Long projectId, Long memberId) {
        ParticipatedUsers pu = participatedUserRepository
                .findByUsersIdAndProjectId(memberId, projectId)
                .orElse(null);
        // 1. 해당 participated 요청을 deny
        participatedUserRepository.delete(pu);

        // 1. 이후 해당 회원에게 로그로 알림
    }

    public void acceptApply(Long projectId, Long memberId) {
        ParticipatedUsers participatedUsers = participatedUserRepository
                .findByUsersIdAndProjectId(memberId, projectId)
                .orElse(null);
        // 1. 이제 승인 되었으니, 해당 프로젝트의 참여 멤버가 됨
        ParticipatedStatus userStatus = participatedUsers.getStatus();
        if (userStatus == READY) {
            participatedUsers.setStatus(MEMBER);
            participatedUsers.getRecruit().participateProject();
        } else {
            throw new AlreadyAppliedProject("이미 프로젝트에 참여 했습니다.");
        }
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
                .orElseThrow(() ->
                        new UserNotFoundException("로그인 후 사용 가능 합니다."));

        // 1. 프로젝트에 연관 되어 있는 Recruit 까지 모두 조회
        Project project = projectRepository
                .findByIdWithRecruits(projectId)
                .orElse(null);

        if (isPresent(projectId, user)) {
            throw new AlreadyAppliedProject("이미 지원한 신청인 입니다.");
        }
        // 2. 지원한 Recruit를 선별
        Recruit recruit1 = project.getRecruits()
                .stream().filter(recruit -> detailField.equals(recruit.getDetailField()))
                .findFirst().orElse(null);
        // 3. 해당 Recruit 에 지원

        ParticipatedUsers participatedUsers = ParticipatedUsers
                .makeParticipatedUserAsMReady(user, project, recruit1);

        participatedUserRepository.save(participatedUsers);
    }

    private boolean isPresent(Long projectId, User user) {
        Optional<ParticipatedUsers> byUsersIdAndProjectId = participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), projectId);

        ParticipatedUsers participatedUsers = byUsersIdAndProjectId.orElse(null);

        log.info("get = {}", participatedUsers);
        return byUsersIdAndProjectId
                .isPresent();
    }

    // 1. 현재 유저 정보를 조회하고, 프로젝트 id로 프로젝트를 조회 한다.
    // 2. 현재 유저 정보가 프로젝트의 관리자 이면, ready 상태인 모든 회원에 대한 정보를 볼 수 있어야 한다.
    public ManagerPageDto getManagerPage(Long projectId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Not login"));

        ParticipatedUsers participatedUsers = participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), projectId)
                .orElse(null);

        if (participatedUsers.getStatus() != ParticipatedStatus.MANAGER) {
            throw new NotAccessibleAuthenticationException("Manager 만 접근 가능");
        }

        return projectRepository.fetchManagerPage(user.getId(), projectId);
    }
    //
    public MemberPages getMemberPage(Long projectId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Not login"));

        ParticipatedUsers participatedUsers = participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), projectId)
                .orElse(null);

        ParticipatedStatus status = participatedUsers.getStatus();

        if ((status != MEMBER) && (status != ParticipatedStatus.MANAGER)) {
            throw new NotAccessibleAuthenticationException("Manager 나 Member 만 접근 가능");
        }

        return projectRepository.fetchMemberPage(projectId, user.getId());
    }
    // 1. project 가 진행 중으로 바뀔 때
    // 2. ready 상태 회원들을 전부 삭제, 종료일을 기일로 부터 14일

    public void startProject(Long id) {
        participatedUserRepository
                .findParticipatedUsersByProjectIdAnAndStatus(id, READY)
                .stream()
                .forEach(participatedUserRepository::delete);

        projectRepository.findById(id)
                .ifPresent(project -> {
                    project.setProjectStatus(ProjectStatus.RUNNING);
                    project.setEndDate(LocalDateTime.now()
                            .plus(14, ChronoUnit.DAYS));
                });
    }

    public void endProject(Long id) {
        // 완료 상태로 만든다. 이제 수정이 불가능 해야함
        projectRepository.findById(id)
                .ifPresent(project ->
                        project.setProjectStatus(ProjectStatus.END));
    }

    public void startAllProject() {
        projectRepository.findAll()
                .stream()
                .filter(project-> project.getCreatedDateTime()
                        .compareTo(LocalDateTime.now()) >= 0)
                .forEach(Project::startProject);
    }

    public void addTimeLine(Long projectId, AddTimeLineForm form, String email) {
        User findUser = userRepository.findByEmail(email)
                .orElse(null);
        participatedUserRepository.findByUsersIdAndProjectId(findUser.getId(), projectId)
                .ifPresent(user -> {
                    TimeLine timeLine = TimeLine.createTimeLine(form, user, null);
                    timeLineRepository.save(timeLine);
                });
    }

    public void checkProjectRecruitmentDate() {
        projectRepository.findAll()
                .stream()
                .filter(project -> (project.getRecruitmentDate().compareTo(LocalDateTime.now()) < 0) && project.getProjectStatus() != ProjectStatus.END)
                .forEach(project -> {
                    project.setProjectStatus(ProjectStatus.RUNNING);
                    project.setEndDate(LocalDateTime.now().plus(14, ChronoUnit.DAYS));
                });
    }

    public void cancelProject(String email, Long projectId) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        log.info("user Id = {}", user.getId());
        log.info("project Id = {}", projectId);

        participatedUserRepository
                .findByUsersIdAndProjectId(user.getId(), projectId)
                .ifPresent(participatedUsers -> {
                    System.out.println(participatedUsers);
                    Recruit recruit = participatedUsers.getRecruit();
                    System.out.println(recruit);
                    recruit.cancelProject();
                    participatedUserRepository.delete(participatedUsers);
                });
    }

    public TimeLineContentDto getTimeLine(Long timeLineId) {
        return timeLineRepository.findById(timeLineId)
                .map(TimeLineContentDto::convert)
                .orElse(null);
    }

    // add job, job list를 추가 한다.
    //
    public void addJob(Long projectId, JobCreateForm form) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Job job = Job.createJob(project, form);
        jobRepository.save(job);
    }

    public void addJobTimeLine(Long jobId, Long projectId, String email, AddTimeLineForm form) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        Job job = jobRepository.findById(jobId).orElse(null);

        ParticipatedUsers participatedUsers = participatedUserRepository.findByUsersIdAndProjectId(user.getId(), projectId)
                .orElse(null);

        TimeLine timeLine = TimeLine.createTimeLine(form, participatedUsers, job);
        timeLineRepository.save(timeLine);
    }


    public List<JobDto> getJobList(Long projectId) {
        return jobRepository.getJobList(projectId);
    }
}
