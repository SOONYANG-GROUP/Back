package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.TimeLine;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TimeLineContentDto {
    private String url;

    private String description;

    private LocalDateTime createJobTime;

    private LocalDateTime updateJobTime;

    public static TimeLineContentDto convert(TimeLine timeLine) {
        return new TimeLineContentDto(timeLine.getUrl(),
                timeLine.getDescription(),
                timeLine.getCreateJobTime(),
                timeLine.getUpdateJobTime());
    }
}
