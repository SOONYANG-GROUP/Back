package com.campuscrew.campuscrew.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"detailField", "userId", "name"})
public class AppliedUserDto {
    private String detailField;
    private Long userId;
    private String name;
}
