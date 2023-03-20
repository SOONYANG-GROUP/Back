package com.campuscrew.campuscrew.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class JoinedUser {
    @Id
    @Column(name = "joined_user_id")
    private String id;

    private String field;

    private String developer;

    private Integer maxRecruits;

    private Integer recruits;
}
