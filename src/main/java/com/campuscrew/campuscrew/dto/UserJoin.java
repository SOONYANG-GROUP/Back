package com.campuscrew.campuscrew.dto;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoin {
    private String username;
    private String nickname;
    private String name;
    private Integer age;
    private String password;
    private String email;
}
