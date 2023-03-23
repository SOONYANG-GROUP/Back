package com.campuscrew.campuscrew.domain.user;

import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.domain.board.JoinedUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@ToString(of = {"email", "socialId"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private Integer age;

    private String password;

    private String email;

    private String nickname;

    private String name;

    private String socialId;

    private String refreshToken;


    @OneToMany(mappedBy = "user")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<JoinedUser> joinedUsers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;


    public void addBoard(Project project) {
        this.projects.add(project);
        project.addUser(this);
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
