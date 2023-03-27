package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.domain.user.Role;
import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
