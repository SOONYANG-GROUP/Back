package com.campuscrew.campuscrew.repository.participateduser;

import com.campuscrew.campuscrew.dto.TimeLineContent;
import com.campuscrew.campuscrew.repository.project.TimeLineListTitleDto;

import java.util.List;

public interface TimeLineRepositoryCustom {
    List<TimeLineListTitleDto> getTimeLineListTitles(Long participatedUserId);
    public TimeLineContent getTimeLineContent(Long timeLineId);
}
