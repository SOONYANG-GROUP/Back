package com.campuscrew.campuscrew.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinSuccessDto {
    private String username;
    private Integer age;
    private String nickname;
    private String name;
    private String email;
}
