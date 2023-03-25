package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private ProjectStatus status;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;     user -- joinuser --
//
    //1. 연관 관계를 고려해보자. ex) 모든 user 가 project 에 대해 직접적으로 알아야 할 필요가 있을까?
    //2. JoinedUser 라는 mapping 테이블에서 이를 관리하면 될 것인데
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Recruit> recruits = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ParticipatedUsers> participatedUsers = new ArrayList<>(); //

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Reference> references = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime startWithDate;

    private LocalDateTime endDate;

    private LocalDateTime recruitmentDate;

    private LocalDateTime createdDateTime;


    public static Project createProject(AddProjectDto dto) {
        Project project = new Project();
        project.setTitle(dto.getTitle());
        project.setStatus(ProjectStatus.READY);
        project.setDescription(dto.getDescription());
        project.setOpenChatUrl(dto.getOpenChatUrl());
        project.setVoiceChatUrl(dto.getVoiceChatUrl());
        project.setCreatedDateTime(LocalDateTime.now());
        project.setRecruitmentDate(LocalDateTime.parse("2020-10-10"));
        project.addRecruits(dto.dtoToRecruit()); // recruit List
        return project;
    }

    private void addRecruits(List<Recruit> recruits) {
        for (Recruit recruit : recruits) {
            this.recruits.add(recruit);
            recruit.setProject(this);
        }
    }

//    private void addJoinUser(JoinedUser joinedUser) {
//        this.joinedUsers.add(joinedUser);
//        joinedUser.setProject(this);
//    }

}
