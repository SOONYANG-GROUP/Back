package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.dto.project.AddProjectDto;
import com.campuscrew.campuscrew.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final BoardService boardService;
    @PostMapping("/add")
    public String addBoard(@RequestBody AddProjectDto addProjectDto,
                           @AuthenticationPrincipal UserDetails userDetails,
                           HttpServletResponse response) {
        String email = userDetails.getUsername();
        log.info("email = {}", email);
        boardService.addProject(email, addProjectDto);
        System.out.println();
        return null;
    }
}
