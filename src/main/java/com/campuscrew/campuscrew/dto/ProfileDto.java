package com.campuscrew.campuscrew.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String name;
    private String nickname;
    private String selfIntroduction;
    private String shortIntroduction;
    private String detailField;

    // 로그 출력 기능
    // 1. 프로젝트에 참가 했거나, 로드맵에 기술을 추가하거나,
    // 2. 회원에 대한 변경 정보를 추적

    // 회원의 상태 ?
    //
}