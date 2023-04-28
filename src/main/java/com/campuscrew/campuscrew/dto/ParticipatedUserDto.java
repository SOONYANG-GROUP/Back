package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.ParticipatedStatus;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"detailField", "name"})
public class ParticipatedUserDto {
    private Long id;
    private ParticipatedStatus status;
    private Long memberId;
    private List<TimeLineListDto> timeLineListDtos;
    private String detailField;
    private String name;
}
//GroupBy.list(Projections.constructor(TimeLineListDto.class,
//                                        timeLine.id,
//                                        timeLine.title)),