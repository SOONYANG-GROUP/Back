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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    private String openChatUrl;

    private String voiceChatUrl;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;     user -- joinuser --

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<JoinedUser> joinedUsers = new ArrayList<>(); //

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Recruit> recruits = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Reference> references = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime updatedDateTime;

    private LocalDateTime createdDateTime;

    private LocalDateTime startWithDate;

    public static Project createProject(JoinedUser joinedUser, AddProjectDto dto) {
        Project project = new Project();
        project.setDescription(dto.getDescription());
        project.setOpenChatUrl(dto.getOpenChatUrl());
        project.setVoiceChatUrl(dto.getVoiceChatUrl());
        project.setCreatedDateTime(LocalDateTime.now());
        project.addRecruits(dto.dtoToRecruit()); // recruit List
        project.addJoinUser(joinedUser);
        return project;
    }

    private void addRecruits(List<Recruit> recruits) {
        for (Recruit recruit : recruits) {
            this.recruits.add(recruit);
            recruit.setProject(this);
        }
    }

    private void addJoinUser(JoinedUser joinedUser) {
        this.joinedUsers.add(joinedUser);
        joinedUser.setProject(this);
    }

}
