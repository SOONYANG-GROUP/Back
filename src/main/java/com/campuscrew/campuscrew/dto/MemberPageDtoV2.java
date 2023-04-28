package com.campuscrew.campuscrew.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class MemberPageDtoV2 implements MemberPages{
    private List<MemberPageDto> memberPageDtos;
    private List<TimeLineListDto> timeLineListDtos;
}
