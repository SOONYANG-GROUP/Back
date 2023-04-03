package com.campuscrew.campuscrew.dto;

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
    private String detailField;
    private String name;
}
