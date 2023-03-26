package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Recruit;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@ToString(of = {"field", "detailField", "maxRecruit", "currentRecruit"})
public class RecruitUserDto {
    private String field;
    private String detailField;
    private Integer maxRecruit;
    private Integer currentRecruit;

    public RecruitUserDto(String field, String detailField, Integer maxRecruit, Integer currentRecruit) {
        this.field = field;
        this.detailField = detailField;
        this.maxRecruit = maxRecruit;
        this.currentRecruit = currentRecruit;
    }

    public static List<RecruitUserDto> recruitsToDto(List<Recruit> recruits) {
        return recruits.stream()
                .map(RecruitUserDto::recruitUserDto)
                .collect(Collectors.toList());
    }

    private static RecruitUserDto recruitUserDto(Recruit recruit) {
        return new RecruitUserDto(recruit.getField(),
                recruit.getDetailField(), recruit.getMaxRecruit(),
                recruit.getCurrentRecruit());
    }
}
