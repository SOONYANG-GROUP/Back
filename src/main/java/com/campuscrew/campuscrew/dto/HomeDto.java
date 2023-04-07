package com.campuscrew.campuscrew.dto;

import com.campuscrew.campuscrew.dto.project.CountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    private List<HomeCardDto> homeCardDtos;
    private Long countUser;
    private List<CountDto> countDto;
}
