package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    private String openChatUrl;

    private String voiceChatUrl;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;     user -- joinuser --
//
    //1. 연관 관계를 고려해보자. ex) 모든 user 가 project 에 대해 직접적으로 알아야 할 필요가 있을까?
    //2. JoinedUser 라는 mapping 테이블에서 이를 관리하면 될 것인데

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<Recruit> recruits = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<ParticipatedUsers> participatedUsers = new ArrayList<>(); //

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<Reference> references = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime startWithDate;

    private LocalDateTime endDate;

    private LocalDateTime recruitmentDate;

    private LocalDateTime createdDateTime;


    public static Project createProject(User findUser, AddProjectDto dto) {
        Project project = new Project();
        findUser.addProject(project);
        project.setTitle(dto.getTitle());
        project.setProjectStatus(ProjectStatus.READY);
        project.setDescription(dto.getDescription());
        project.setOpenChatUrl(dto.getOpenChatUrl());
        project.setVoiceChatUrl(dto.getVoiceChatUrl());
        project.setCreatedDateTime(LocalDateTime.now());
        project.setStartWithDate(LocalDateTime.of(LocalDate.parse(dto.getRecruitmentDate()), LocalTime.now()));
        project.setRecruitmentDate(LocalDateTime.of(LocalDate.parse(dto.getRecruitmentDate()),
                LocalTime.now()));
        project.addRecruits(dto.dtoToRecruit()); // recruit List
        return project;
    }

    private void addRecruits(List<Recruit> recruits) {
        for (Recruit recruit : recruits) {
            this.recruits.add(recruit);
            recruit.setProject(this);
        }
    }

    public static void startProject(Project project) {
        project.setProjectStatus(ProjectStatus.RUNNING);
    }

    public static void endProject(Project project) {
        project.setProjectStatus(ProjectStatus.END);
    }



//    private void addJoinUser(JoinedUser joinedUser) {
//        this.joinedUsers.add(joinedUser);
//        joinedUser.setProject(this);
//    }

}
/*
    1. 조회가 됨.
    2. 팀원, 매니저 >> participatedUser 테이블에서 관리
    3. 팀원란을 클릭, 이 프로젝트에 대해 참여를 안했다.
    4. user 정보를 조회 현재 세션으로 로그인 되어 있는 유저
    5. 로그인 했을 때 자기가 작성한 프로젝트 확인 가능

 */