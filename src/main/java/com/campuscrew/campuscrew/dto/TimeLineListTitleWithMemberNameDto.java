package com.campuscrew.campuscrew.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLineListTitleWithMemberNameDto {
    private Long timeLineId;
    private String title;
    private LocalDateTime createTimeLineDateTime;
    private String memberName;
}
