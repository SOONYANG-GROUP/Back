package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.domain.board.Comment;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.dto.AddCommentDto;
import com.campuscrew.campuscrew.dto.AddSubCommentDto;
import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
import com.campuscrew.campuscrew.repository.project.CommentPageDto;
import com.campuscrew.campuscrew.repository.project.SubCommentDto;
import com.campuscrew.campuscrew.service.ProjectService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
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

    @GetMapping("/{id}/comment")
    public CommentPageDto getComment(@PathVariable Long id) {
        return projectService.getCommentPage(id);
    }

    @GetMapping
    public HomeDto homePage() {
        return projectService.getHomePage();
    }

    // 참여 버튼을 누르면 호출되는 api
    // 1. 현재 로그인 되어 있는 user 정보를 조회
    // 2. 현재 프로젝트의 정보를 조회
    @PostMapping("/{id}/join")
    public String applyProject(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        projectService.applyProject(id, email);
        return "ok";
    }
}
