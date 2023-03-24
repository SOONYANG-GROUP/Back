package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Recruit;
import com.campuscrew.campuscrew.domain.board.Reference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceDto implements Serializable {
    private String url;

    public static List<ReferenceDto> referencesToDto(List<Reference> references) {
        return references.stream()
                .map(ReferenceDto::referenceDto)
                .collect(Collectors.toList());
    }

    private static ReferenceDto referenceDto(Reference reference) {
        return new ReferenceDto(reference.getUrl());
    }
}
