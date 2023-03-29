package com.campuscrew.campuscrew.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserJoin implements Serializable {
    private String name;
    private String detailField;
    private String selfIntroduction;
    private String shortIntroduction;
    private String password;
    private String email;
}
