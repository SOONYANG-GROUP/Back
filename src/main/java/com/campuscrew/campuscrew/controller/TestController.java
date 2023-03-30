package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.domain.board.Project;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import com.campuscrew.campuscrew.repository.project.ProjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectRepository repository;

    @ResponseBody
    @GetMapping("/test/project")
    public String testPro(@RequestParam Long id) {
        Project project = repository.findByIdWithRecruits(1L)
                .orElse(null);
        project.getRecruits()
                .stream().forEach(System.out::println);
        return "ok";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "ok";
    }


    @GetMapping("/user")
    @ResponseBody
    public String checkUser(Authentication authentication,
                            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("getUsername(email) = {}", userDetails.getUsername());
        System.out.println(authentication);
        return "ok";
    }

    @GetMapping("/oauth/user")
    public String checkOauthUser(Authentication authentication,
                                 @AuthenticationPrincipal OAuth2User user) {
        if(user == null) {
            return "redirect:/loginForm";
        }
        System.out.println(user.getAttributes());
        return "ok";
    }
}
