package com.campuscrew.campuscrew.repository.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SubCommentDto {
    private Long subCommentId;
    private String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createDate;
    private String subComment;

    public SubCommentDto(Long subCommentId, String name, LocalDate createDate, String subComment) {
        this.subCommentId = subCommentId;
        this.name = name;
        this.createDate = createDate;
        this.subComment = subComment;
    }
}
