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

    @GetMapping("/{id}/member")
    public MemberPageDto getMemberPage(@PathVariable Long id,
                                       @AuthenticationPrincipal
                                       UserDetails userDetails) {
        String email = userDetails.getUsername();
        return projectService.getMemberPage(id, email);
    }

    @PostMapping("/{id}/members/edit")
    public String addContent(@RequestBody MemberEditForm editForm,
                             @PathVariable Long id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        projectService.addMemberContent(id, editForm, userDetails.getUsername());
        return "pok";
    }

    @GetMapping("/set")
    public String setRunningProject() {
        projectService.checkProjectRecruitmentDate();
        return "ok";
    }
}
