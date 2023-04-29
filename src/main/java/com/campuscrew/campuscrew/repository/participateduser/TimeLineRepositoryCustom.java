package com.campuscrew.campuscrew.repository.participateduser;

import com.campuscrew.campuscrew.dto.TimeLineContent;
import com.campuscrew.campuscrew.dto.TimeLineListTitleWithMemberNameDto;
import com.campuscrew.campuscrew.repository.project.TimeLineListTitleDto;

import java.util.List;

public interface TimeLineRepositoryCustom {
    List<TimeLineListTitleDto> getTimeLineListTitles(Long participatedUserId);
    TimeLineContent getTimeLineContent(Long timeLineId);
    List<TimeLineListTitleWithMemberNameDto> getTimeLineListTitleWithName(Long jobId);
}
