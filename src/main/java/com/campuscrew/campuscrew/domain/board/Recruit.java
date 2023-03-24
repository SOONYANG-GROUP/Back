package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer maxRecruit;

    private Integer currentRecruit;

    private String field;

    private String detailField;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private JoinedUser joinedUser;

    public static Recruit makeRecruit(RecruitUserDto dto) {
        Recruit recruit = new Recruit();
        recruit.setField(dto.getField());
        recruit.setDetailField(dto.getDetailField());
        recruit.setCurrentRecruit(0);
        recruit.setMaxRecruit(dto.getTotalNum());
        return recruit;
    }
}
