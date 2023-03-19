package com.campuscrew.campuscrew.domain;

import jakarta.persistence.*;

@Entity
public class Board {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
