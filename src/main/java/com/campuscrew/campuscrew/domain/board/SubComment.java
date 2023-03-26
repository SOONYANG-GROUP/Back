package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    private String subComment;


    public static SubComment makeSubComment(User user, Comment findComment, String comment) {
        SubComment subComment1 = new SubComment();
        subComment1.setSubComment(comment);
        findComment.addSubComment(subComment1);
        subComment1.addUser(user);
        return subComment1;
    }

    private void addUser(User user) {
        this.user = user;
        user.getSubComments().add(this);
    }
}
