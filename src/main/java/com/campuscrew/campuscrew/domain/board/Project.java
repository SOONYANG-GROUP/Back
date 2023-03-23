package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Project {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "project")
    private List<JoinedUser> joinedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Reference> references = new ArrayList<>();

    private LocalDateTime updatedDateTime;

    private LocalDateTime createdDateTime;

    private LocalDateTime startWithDate;
}
