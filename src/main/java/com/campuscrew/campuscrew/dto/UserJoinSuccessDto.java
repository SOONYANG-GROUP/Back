package com.campuscrew.campuscrew.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinSuccessDto {
    private String name;
    private String email;
    private String selfIntroduction;
    private String shortIntroduction;
    private String detailField;
}
