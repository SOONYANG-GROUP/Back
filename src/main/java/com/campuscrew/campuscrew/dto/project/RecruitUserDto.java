package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitUserDto {
    private String field;
    private String detailField;
    private Integer totalNum;

    public static List<RecruitUserDto> recruitsToDto(List<Recruit> recruits) {
        return recruits.stream()
                .map(RecruitUserDto::recruitUserDto)
                .collect(Collectors.toList());
    }

    private static RecruitUserDto recruitUserDto(Recruit recruit) {
        return new RecruitUserDto(recruit.getField(),
                recruit.getDetailField(), recruit.getMaxRecruit());
    }
}
