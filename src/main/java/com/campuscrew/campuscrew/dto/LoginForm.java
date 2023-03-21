package com.campuscrew.campuscrew.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginForm {
    private String email;
    private String password;
}
