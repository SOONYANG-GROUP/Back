package com.campuscrew.campuscrew.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoin implements Serializable {
    private String username;
    private String nickname;
    private String name;
    private Integer age;
    private String password;
    private String email;
}
