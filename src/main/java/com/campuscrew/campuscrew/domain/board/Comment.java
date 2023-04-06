package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "comment",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<SubComment> subComments = new ArrayList<>();

    private String comment;

    private LocalDateTime createTime;

    private Integer subCommentCount;

    public static Comment createComment(User user, String comment, Project project) {
        Comment added = new Comment();
        added.subCommentCount = 0;
        added.setComment(comment);
        added.setCreateTime(LocalDateTime.now());
        added.addProject(project);
        added.addUser(user);
        return added;
    }

    private void addUser(User user) {
        this.user = user;
        user.getComments().add(this);
    }

    private void addProject(Project project) {
        this.project = project;
        project.getComments().add(this);
    }

    public void addSubComment(SubComment subComment) {
        this.subCommentCount++;
        this.subComments.add(subComment);
        subComment.setComment(this);
    }
}

