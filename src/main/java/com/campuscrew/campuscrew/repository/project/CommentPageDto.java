package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPageDto {
    private List<CommentDto> commentDtos;
}
