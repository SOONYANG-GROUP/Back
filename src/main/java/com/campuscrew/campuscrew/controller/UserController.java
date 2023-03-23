package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.dto.UserJoin;
import com.campuscrew.campuscrew.dto.UserJoinSuccessDto;
import com.campuscrew.campuscrew.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/join", consumes = "application/json")
    public UserJoinSuccessDto join(@RequestBody
                           UserJoin userJoin) throws Exception {
        log.info("userJoin = {}", userJoin);
        userService.signUp(userJoin);
        return UserJoinSuccessDto.builder()
                .age(userJoin.getAge())
                .nickname(userJoin.getNickname())
                .name(userJoin.getName())
                .email(userJoin.getEmail())
                .build();
    }

    @PostMapping("/jwt-test")
    public String jwtTest(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        System.out.println(email);
        return "jwtTest";
    }

    @GetMapping("/test/authentication")
    public Authentication authentication(Authentication authentication) {
        log.info("authentication = {}", authentication);
        return authentication;
    }
//    @GetMapping("/test/session")
//    public String jwtSession(Authentication authentication) {
//
//    }
}
