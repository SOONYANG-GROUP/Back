package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.auth.PrincipalDetails;
import com.campuscrew.campuscrew.domain.Role;
import com.campuscrew.campuscrew.domain.User;
import com.campuscrew.campuscrew.dto.UserJoin;
import com.campuscrew.campuscrew.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Controller
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/user")
    @ResponseBody
    public String checkUser(Authentication authentication) {
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

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/joindd")
    public String join(@ModelAttribute User user) {
        var oldPwd = user.getPassword();
        var newPwd = passwordEncoder.encode(user.getPassword());
        user.setRole(Role.USER);
        user.setPassword(newPwd);
        userRepository.save(user);
        return "redirect:/";
    }

}
