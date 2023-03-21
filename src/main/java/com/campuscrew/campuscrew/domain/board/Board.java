package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.User;
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
public class Board {
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

    @OneToMany(mappedBy = "board")
    private List<JoinedUser> joinedUsers = new ArrayList<>();


    @OneToMany(fetch = LAZY)
    private List<Reference> references = new ArrayList<>();

    public void addUser(User user) {
        this.user = user;
    }

    private LocalDateTime updatedDateTime;

    private LocalDateTime createdDateTime;

    private LocalDateTime startWithDate;
}
