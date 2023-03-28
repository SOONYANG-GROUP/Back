package com.campuscrew.campuscrew.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "detailField"})
public class ProfileDto {
    private Long id;
    private String name;
    private String detailField;
    // 로그 출력 기능
    // 1. 프로젝트에 참가 했거나, 로드맵에 기술을 추가하거나,
    // 2. 회원에 대한 변경 정보를 추적


}