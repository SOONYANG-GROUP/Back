package com.campuscrew.campuscrew.domain.board;


import com.campuscrew.campuscrew.dto.JobCreateForm;
import com.campuscrew.campuscrew.dto.SimpleJobForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private JobState state;
    private LocalDateTime startJobDate;

    private LocalDateTime updateJobDate;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<TimeLine> timeLines = new ArrayList<>();

    @Builder
    public Job(Project project, String jobTitle, String jobDescription, LocalDateTime startJobDate,
               LocalDateTime updateJobDate,
               JobState state) {
        this.project = project;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.startJobDate = startJobDate;
        this.updateJobDate = updateJobDate;
        this.state = state;
    }

    public static Job createJob(Project project, JobCreateForm form) {
        return Job.builder()
                .jobDescription(form.getJobDescription())
                .jobTitle(form.getJobTitle())
                .state(JobState.JOB)
                .project(project)
                .startJobDate(LocalDateTime.now())
                .updateJobDate(LocalDateTime.now())
                .build();
    }
    public static Job createSimpleJob(Project project, SimpleJobForm form) {
        return Job.builder()
                .jobDescription(form.getDescription())
                .jobTitle(form.getName())
                .state(JobState.OPINION)
                .project(project)
                .startJobDate(LocalDateTime.now())
                .updateJobDate(LocalDateTime.now())
                .build();
    }

    public void updateJobDate() {
        this.updateJobDate = LocalDateTime.now();
    }

}
