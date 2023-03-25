package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Project;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMainDto {
    private List<RecruitUserDto> RecruitUserDtos; // project - recruit
    private List<ReferenceDto> ReferenceDtos; // project - reference
    private Long id;
    private LocalDateTime createDateTime;
    private String title;
    private String description; // project



    // dto -> domain, domain -> dto;
    // Domainconverter

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
