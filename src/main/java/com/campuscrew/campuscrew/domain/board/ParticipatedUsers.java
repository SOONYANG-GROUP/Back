package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "participated_users")
public class ParticipatedUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_users_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ParticipatedStatus status;

    public static ParticipatedUsers makeParticipatedUserAsManager(User user, Project project) {
        ParticipatedUsers participatedUsers = new ParticipatedUsers();
        participatedUsers.setStatus(ParticipatedStatus.MANAGER);
        participatedUsers.participateProject(user, project);
        return participatedUsers;
    }

    public static ParticipatedUsers makeParticipatedUserAsMember(User user, Project project) {
        ParticipatedUsers participatedUsers = new ParticipatedUsers();
        participatedUsers.setStatus(ParticipatedStatus.MEMBER);
        participatedUsers.participateProject(user, project);
        return participatedUsers;
    }



    public static ParticipatedUsers makeParticipatedUserAsMReady(User user, Project project) {
        ParticipatedUsers participatedUsers = new ParticipatedUsers();
        participatedUsers.setStatus(ParticipatedStatus.READY);
        participatedUsers.participateProject(user, project);
        return participatedUsers;
    }

    public static ParticipatedUsers getDefaultParticipatedUser() {
        ParticipatedUsers participatedUsers = new ParticipatedUsers();
        return participatedUsers;
    }

    private void participateProject(User user, Project project) {
        this.setProject(project);
        project.getParticipatedUsers().add(this);
        this.setUser(user);
        user.getJoinedUsers().add(this);
    }

}