package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity()
@NoArgsConstructor
@AllArgsConstructor
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    private String subComment;

}
