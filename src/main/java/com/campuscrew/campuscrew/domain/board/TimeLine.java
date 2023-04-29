package com.campuscrew.campuscrew.domain.board;

import com.campuscrew.campuscrew.dto.AddTimeLineForm;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimeLine {
    @Id
    @Column(name = "timeline_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @Lob
    private String description;

    private LocalDateTime createJobTime;

    private LocalDateTime updateJobTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participated_users_id")
    private ParticipatedUsers participatedUsers;

    @Builder
    public TimeLine(String title, String url, String description,
                    LocalDateTime createJobTime, LocalDateTime updateJobTime, ParticipatedUsers users) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.createJobTime = createJobTime;
        this.updateJobTime = updateJobTime;
        this.participatedUsers = users;
    }

    public static TimeLine createTimeLine(AddTimeLineForm form, ParticipatedUsers users) {
        return TimeLine.builder()
                .createJobTime(LocalDateTime.now())
                .updateJobTime(LocalDateTime.now())
                .url(form.getUrl())
                .description(form.getDescription())
                .title(form.getTitle())
                .users(users)
                .build();
    }
}
