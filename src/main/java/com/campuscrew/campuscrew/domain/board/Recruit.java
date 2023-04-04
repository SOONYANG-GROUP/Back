package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.dto.project.RecruitUserDto;
import com.campuscrew.campuscrew.service.exception.AlreadyAppliedProject;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // ready인 상태에서 거절을 할 수 있다.
    // fetch join 으로 조회 거절한 회원의 user_id 정보로 삭제
    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ParticipatedUsers> participatedUsers;


    public static Recruit makeRecruit(RecruitUserDto dto) {
        Recruit recruit = new Recruit();
        recruit.setField(dto.getField());
        recruit.setDetailField(dto.getDetailField());
        recruit.setCurrentRecruit(0);
        recruit.setMaxRecruit(dto.getMaxRecruit());
        return recruit;
    }

    public void participateProject() {
        if(maxRecruit > currentRecruit) {
            currentRecruit += 1;
        } else {
            throw new IllegalStateException("이미 projects 인원이 가득 찼습니다.");
        }
    }
}
