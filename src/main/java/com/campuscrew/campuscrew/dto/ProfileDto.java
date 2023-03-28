package com.campuscrew.campuscrew.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "detailField"})
public class ProfileDto {
    private Long id;
    private String name;
    private String detailField;
    private String selfIntroduction;
    private String shortIntroduction;
    // 로그 출력 기능

    private List<ProjectGroupDto> projectGroupDtos;
}