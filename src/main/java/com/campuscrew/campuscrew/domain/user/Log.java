package com.campuscrew.campuscrew.domain.user;

import jakarta.persistence.*;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String log;

    private LogStatus logStatus;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
}
