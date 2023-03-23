package com.campuscrew.campuscrew.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequiredUserDto {
    private String field;
    private String detailField;
    private Integer totalNum;
}
