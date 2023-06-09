package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.domain.board.Comment;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.dto.*;
import com.campuscrew.campuscrew.dto.project.AddCommentDto;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.AddSubCommentDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.project.CommentPageDto;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import com.campuscrew.campuscrew.repository.project.SubCommentDto;
import com.campuscrew.campuscrew.service.ProjectService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectRepository repository;

    @GetMapping("/{id}/start")
    public String startProject(@PathVariable Long id) {
        projectService.startProject(id);
        return "ok";
    }

    @GetMapping("/{id}/end")
    public String endProject(@PathVariable Long id) {
        projectService.endProject(id);
        return "ok";
    }

    @PostMapping("/add")
    public Long addProject(@RequestBody AddProjectDto addProjectDto,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     HttpServletResponse response,
                                     RedirectAttributes attributes) {
        String email = userDetails.getUsername();
        log.info("email = {}", email);
        Project project = projectService.addProject(email, addProjectDto);
        return project.getId();
    }


    @GetMapping("/{id}")
    public ProjectMainDto mainPage(@PathVariable Long id) {
        log.info("id = {}", id);
        return projectService.getMainPage(id);
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestBody AddCommentDto addCommentDto,
                             @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Comment comment1 = projectService.addComment(email,
                id, addCommentDto.getComment());
        return "ok";
    }

    @PostMapping("/{id}/subcomment")
    public String addSubComment(@PathVariable Long id,
                                @RequestParam Long commentId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                @RequestBody AddSubCommentDto addSubCommentDto) {
        System.out.println("comment = " + commentId);
        String email = userDetails.getUsername();
        projectService.addSubComment(commentId, email, addSubCommentDto.getSubComment());
        return "ok";
    }

    @GetMapping("/{id}/comment/subcomment")
    public List<SubCommentDto> getSubComment(@RequestParam Long commentId) {
        return repository.fetchSubComment(commentId);
    }

    @GetMapping("/{id}/manager")
    public ManagerPageDto getManagerPage(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return projectService.getManagerPage(id, email);
    }

    @GetMapping("/{id}/comment")
    public CommentPageDto getComment(@PathVariable Long id) {
        return projectService.getCommentPage(id);
    }

    @GetMapping
    public HomeDto homePage() {
        return projectService.getHomePage();
    }

    @PostMapping("/{id}/join")
    public String applyProject(@PathVariable Long id,
                              @RequestBody ApplyingFieldDto applyingFieldDto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        projectService.applyProject(id, email, applyingFieldDto.getDetailField());
        return "ok";
    }

    @PostMapping("/{id}/reject")
    public String rejectProject(@PathVariable Long id,
                                @RequestParam Long memberId) {
        projectService.rejectApply(id, memberId);
        return "ok";
    }

    @PostMapping("/{id}/permit")
    public String permitProject(@PathVariable Long id,
                                @RequestParam Long memberId) {
        projectService.acceptApply(id, memberId);
        return "ok";
    }

    @GetMapping("/{projectId}/cancel")
    public String cancelProject(@AuthenticationPrincipal
                                UserDetails userDetails,
                                @PathVariable Long projectId) {
        String email = userDetails.getUsername();
        projectService.cancelProject(email, projectId);
        return "canceled";
    }

    @GetMapping("/{id}/member")
    public MemberPages getMemberPage(@PathVariable Long id,
                                       @AuthenticationPrincipal
                                       UserDetails userDetails) {
        String email = userDetails.getUsername();
        return projectService.getMemberPage(id, email);
    }


    @PostMapping("/{id}/members/timelines/add")
    public String addTimeLine(@RequestBody AddTimeLineForm addTimeLineForm,
                              @PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        projectService.addTimeLine(id, addTimeLineForm, userDetails.getUsername());
        return "pok";
    }

    @GetMapping("/{id}/members/timelines/{timeLineId}")
    public TimeLineContentDto getTimeLineContent(@PathVariable Long timeLineId) {
        return projectService.getTimeLine(timeLineId);
    }


    @GetMapping("/set")
    public String setRunningProject() {
        projectService.checkProjectRecruitmentDate();
        return "ok";
    }

    @PostMapping("/{projectId}/members/simplejobs/add")
    public String addSimpleJob(@PathVariable Long projectId,
                         @AuthenticationPrincipal UserDetails userDetails,
                         @RequestBody SimpleJobForm form) {
        projectService.addSummary(projectId, form);
        return "ok";
    }

    @PostMapping("/{projectId}/members/jobs/add")
    public String addJob(@PathVariable Long projectId,
                         @AuthenticationPrincipal UserDetails userDetails,
                         @RequestBody JobCreateForm jobCreateForm) {
        projectService.addJob(projectId, jobCreateForm);
        return "ok";
    }

    @DeleteMapping("/{projectId}/members/jobs/{jobId}")
    public String removeJob(@PathVariable Long jobId,
                            @AuthenticationPrincipal UserDetails userDetails) {
        projectService.removeJob(jobId);
        return "ok";
    }

    @PostMapping("/{projectId}/members/jobs/{jobId}/timelines")
    public String addJobTimeLine(@PathVariable Long jobId,
                              @PathVariable Long projectId,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @RequestBody AddTimeLineForm form) {
        projectService.addJobTimeLine(jobId, projectId, userDetails.getUsername(), form);
        return "ok";
    }


    @GetMapping("/{projectId}/members/jobs")
    public List<JobDto> getJobDto(@PathVariable Long projectId) {
        return projectService.getJobList(projectId);
    }

    @GetMapping("/{projectId}/members/jobs/{jobId}/timelines")
    public List<TimeLineListTitleWithMemberNameDto> getJobTimeLines(@PathVariable Long jobId,
                                                                    @PathVariable Long projectId, @AuthenticationPrincipal UserDetails userDetails) {
        return projectService.getJobTimeLines(jobId);
    }
}
