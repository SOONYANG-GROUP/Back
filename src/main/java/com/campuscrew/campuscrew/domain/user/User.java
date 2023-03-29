package com.campuscrew.campuscrew.domain.user;

import com.campuscrew.campuscrew.domain.board.Comment;
import com.campuscrew.campuscrew.domain.board.ParticipatedUsers;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.domain.board.SubComment;
import com.campuscrew.campuscrew.dto.EditForm;
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
@Table(name = "users")
@ToString(of = {"email", "socialId"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    private Integer age;
    private String password;

    private String email;

    private String nickname;

    private String name;

    private String selfIntroduction;

    private String shortIntroduction;

    private String detailField;

    private String socialId;
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ParticipatedUsers> joinedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SubComment> subComments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addProject(Project project) {
        project.setUser(this);
        this.projects.add(project);
    }

    public void editUser(EditForm editForm) {
        this.setDetailField(editForm.getDetailField());
        this.setSelfIntroduction(editForm.getSelfIntroduction());
        this.setShortIntroduction(editForm.getShortIntroduction());
    }
}
