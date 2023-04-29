package com.campuscrew.campuscrew.domain.board;


import com.campuscrew.campuscrew.dto.JobCreateForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    private String jobTitle;

    private String jobDescription;

    private LocalDateTime startJobDate;

    private LocalDateTime updateJobDate;

    @Builder
    public Job(Project project, String jobTitle, String jobDescription, LocalDateTime startJobDate, LocalDateTime updateJobDate) {
        this.project = project;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.startJobDate = startJobDate;
        this.updateJobDate = updateJobDate;
    }

    public static Job createJob(Project project, JobCreateForm form) {
        return Job.builder()
                .jobDescription(form.getJobDescription())
                .jobTitle(form.getJobTitle())
                .project(project)
                .startJobDate(LocalDateTime.now())
                .updateJobDate(LocalDateTime.now())
                .build();
    }

}
