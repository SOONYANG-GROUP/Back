package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountDto implements Serializable {
    private String status;
    private Long count;
}
