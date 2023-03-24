package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class JoinedUser {
    @Id
    @Column(name = "joined_user_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private JoinUserStatus status;

    private String field;

    private String developer;

    private Integer maxRecruits;

    private Integer recruits;

}
