package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.FrontServerAttr;
import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.dto.HomeDto;
import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.dto.project.ProjectMainDto;
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
    public String addProject(@RequestBody AddProjectDto addProjectDto,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     HttpServletResponse response, RedirectAttributes redirectAttributes) {
        String email = userDetails.getUsername();
        log.info("email = {}", email);
        Project project = projectService.addProject(email, addProjectDto);
        ProjectMainDto projectMainDto = ProjectMainDto.getProjectMain(project);
        redirectAttributes.addAttribute("id", projectMainDto.getId());

        return "redirect:http://localhost:3000/projects/{id}";
    }


    @GetMapping("/{id}")
    public ProjectMainDto mainPage(@PathVariable Long id) {
        log.info("id = {}", id);
        return projectService.getMainPage(id);
    }

//
//    private String BuildRedirectFrontUrl(String) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("redirect:");
//        builder.append(FrontServerAttr.FRONT_URL);
//    }
}
