package com.campuscrew.campuscrew.domain.user;

import com.campuscrew.campuscrew.domain.board.Project;
import jakarta.persistence.*;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;

    private String url;

    private String description;

    private LogStatus logStatus;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    private Project project;
}
