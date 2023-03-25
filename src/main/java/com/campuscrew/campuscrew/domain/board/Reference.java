package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.dto.project.ReferenceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reference {
    @Id
    @Column(name = "reference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public static Reference dtoToReference(ReferenceDto dto) {
        return Reference.builder()
                .url(dto.getUrl())
                .build();
    }
}
