package com.campuscrew.campuscrew.repository.project;

import com.campuscrew.campuscrew.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class CommentPageDto {
    private Integer subCommentCount;
    private List<CommentDto> commentDtos;
}
