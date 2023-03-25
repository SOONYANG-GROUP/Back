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

    public static ParticipatedUsers createAsManager(User findUser, Project project) {
        ParticipatedUsers defaultJoinedUser = getDefaultJoinedUser();
        defaultJoinedUser.setStatus(ParticipatedStatus.MANAGER);
        defaultJoinedUser.participateProject(findUser, project);
        return defaultJoinedUser;
    }

    private static ParticipatedUsers getDefaultJoinedUser() {
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
