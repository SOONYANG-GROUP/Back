package com.campuscrew.campuscrew.repository.user;

import com.campuscrew.campuscrew.dto.user.AlarmDto;

import java.util.List;

public interface AlarmRepositoryCustom {
    List<AlarmDto> fetchAlarmList(Long memberId);
}
