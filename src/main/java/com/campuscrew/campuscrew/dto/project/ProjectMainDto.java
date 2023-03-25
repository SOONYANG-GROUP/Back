package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Project;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMainDto implements Serializable {
    private List<RecruitUserDto> RecruitUserDtos; // project - recruit
    private List<ReferenceDto> ReferenceDtos; // project - reference
    private Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDateTime;

    private String title;

    private String description; // project


    public static ProjectMainDto getProjectMain(Project project) {
        ProjectMainDto dto = new ProjectMainDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setCreateDateTime(project.getCreatedDateTime());
        dto.setRecruitUserDtos(RecruitUserDto.recruitsToDto(project.getRecruits()));
        dto.setReferenceDtos(ReferenceDto.referencesToDto(project.getReferences()));
        return dto;
    }
}
