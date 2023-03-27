package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "field", "detailField", "currentRecruit", "maxRecruit"})
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
        recruit.setMaxRecruit(dto.getMaxRecruit());
        return recruit;
    }
}
