package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.dto.UserJoin;
import com.campuscrew.campuscrew.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody UserJoin userJoin) throws Exception {
        userService.signUp(userJoin);
        return "회원가입 성공";
    }

    @PostMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest";
    }
    @GetMapping("/test/authentication")
    public Authentication authentication(Authentication authentication) {
        log.info("authentication = {}", authentication);
        return authentication;
    }
}
