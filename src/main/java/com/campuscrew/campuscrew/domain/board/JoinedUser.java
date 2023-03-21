package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.User;
import com.campuscrew.campuscrew.repository.UserRepository;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Board board;

    private String field;

    private String developer;

    private Integer maxRecruits;

    private Integer recruits;

}
