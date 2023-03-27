package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Recruit;
import com.campuscrew.campuscrew.domain.board.Reference;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(of = {"url"})
public class ReferenceDto implements Serializable {
    private String url;

    public ReferenceDto(String url) {
        this.url = url;
    }

    public static List<ReferenceDto> referencesToDto(List<Reference> references) {
        return references.stream()
                .map(ReferenceDto::referenceDto)
                .collect(Collectors.toList());
    }
    private static ReferenceDto referenceDto(Reference reference) {
        return new ReferenceDto(reference.getUrl());
    }
}
