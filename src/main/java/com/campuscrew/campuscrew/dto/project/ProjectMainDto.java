package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.domain.board.ProjectStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMainDto implements Serializable {
    private ProjectStatus projectStatus;
    private List<RecruitUserDto> recruitUserDtos; // project - recruit
    private List<ReferenceDto> referenceDtos; // project - reference
    private Long id;

    @Override
    public String toString() {
        return "ProjectMainDto{" +
                "recruitUserDtos=" + recruitUserDtos +
                ", referenceDtos=" + referenceDtos +
                ", id=" + id +
                ", createDateTime=" + createDateTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

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
