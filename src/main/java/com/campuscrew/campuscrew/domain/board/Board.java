package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class Board {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @OneToMany
    private List<JoinedUser> joinedUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime updatedDateTime;

    private LocalDateTime createdDateTime;

    private LocalDateTime startWithDate;


}
