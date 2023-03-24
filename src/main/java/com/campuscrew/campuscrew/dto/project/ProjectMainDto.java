package com.campuscrew.campuscrew.dto.project;

import com.campuscrew.campuscrew.domain.board.Project;
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
    private Long id;
    private LocalDateTime createDate;
    private String title;
    private String description;
    private List<RecruitUserDto> recruitUsers;
    private List<ReferenceDto> references;
    // dto -> domain, domain -> dto;
    // Domainconverter
    public static ProjectMainDto getProjectMain(Project project) {
        ProjectMainDto dto = new ProjectMainDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setCreateDate(project.getCreatedDateTime());
        dto.setRecruitUsers(RecruitUserDto.recruitsToDto(project.getRecruits()));
        dto.setReferences(ReferenceDto.referencesToDto(project.getReferences()));
        return dto;
    }
}
