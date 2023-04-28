package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.domain.board.TimeLine;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TimeLineContentDto {
    private String url;

    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createJobTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateJobTime;

    public static TimeLineContentDto convert(TimeLine timeLine) {
        return new TimeLineContentDto(timeLine.getUrl(),
                timeLine.getDescription(),
                timeLine.getCreateJobTime(),
                timeLine.getUpdateJobTime());
    }
}
