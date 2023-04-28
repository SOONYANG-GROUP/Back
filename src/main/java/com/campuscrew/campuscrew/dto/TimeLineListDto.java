package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.repository.project.TimeLineListTitleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TimeLineListDto {
    private Long participatedUsersId;
    private List<TimeLineListTitleDto> timeLineListTitleDtos;
}
