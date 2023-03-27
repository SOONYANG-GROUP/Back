package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.domain.board.Comment;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.dto.AddCommentDto;
import com.campuscrew.campuscrew.dto.AddSubCommentDto;
import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.ManagerPageDto;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
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
    @PostMapping("/add")
    public Long addProject(@RequestBody AddProjectDto addProjectDto,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     HttpServletResponse response, RedirectAttributes redirectAttributes) {
        String email = userDetails.getUsername();
        log.info("email = {}", email);
        Project project = projectService.addProject(email, addProjectDto);
        ProjectMainDto projectMainDto = ProjectMainDto.getProjectMain(project);
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
                              @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        projectService.applyProject(id, email);
        return "ok";
    }

}