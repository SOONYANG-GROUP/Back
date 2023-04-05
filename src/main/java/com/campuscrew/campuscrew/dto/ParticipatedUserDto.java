package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.ParticipatedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString(of = {"detailField", "name"})
public class ParticipatedUserDto {
    private Long id;
    private ParticipatedStatus status;
    private Long memberId;
    private String url;
    private String description;
    private String detailField;
    private String name;
}
