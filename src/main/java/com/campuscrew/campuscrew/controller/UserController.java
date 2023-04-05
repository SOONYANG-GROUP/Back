package com.campuscrew.campuscrew.controller;

import com.campuscrew.campuscrew.dto.*;
import com.campuscrew.campuscrew.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/edit")
    public String editUser(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestBody EditForm editForm) {

        String email = userDetails.getUsername();
        userService.editUser(editForm, email);
        return "ok";
    }

    @PostMapping(value = "/join", consumes = "application/json")
    public UserJoinSuccessDto join(@RequestBody
                                       UserJoin userJoin) throws Exception {
        log.info("userJoin = {}", userJoin);
        userService.signUp(userJoin);
        return UserJoinSuccessDto.builder()
                .detailField(userJoin.getDetailField())
                .shortIntroduction(userJoin.getShortIntroduction())
                .selfIntroduction(userJoin.getSelfIntroduction())
                .name(userJoin.getName())
                .email(userJoin.getEmail())
                .build();
    }

    @GetMapping("/profile")
    public ProfileDto getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        ProfileDto profile = userService.getProfile(email);
        return profile;
    }

    @GetMapping("/profile/{id}")
    public ProfileDto getOtherUserProfile(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable Long id) {
        ProfileDto profile = userService.getProfile(id);
        return profile;
    }



    @GetMapping("/exlogin")
    public String redirect(@RequestParam String accessToken,
                           @RequestParam String refreshToken,
                           HttpServletResponse response) throws IOException {
        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        response.sendRedirect(redirectUrl);
        return "ok";
    }
}
