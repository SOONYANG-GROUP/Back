package com.campuscrew.campuscrew.oauth2.handler;

import com.campuscrew.campuscrew.domain.user.Role;
import com.campuscrew.campuscrew.dto.TokenDto;
import com.campuscrew.campuscrew.jwt.JwtService;
import com.campuscrew.campuscrew.oauth2.CustomOAuth2User;
import com.campuscrew.campuscrew.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = Optional
                .ofNullable(request.getParameter("redirectUrl"))
                .orElse(null);// ?redirectUrl=?

        log.info("OAuth2 성공");
        log.info("redirectUrl = {}", redirectUrl);
        String attribute = Optional.ofNullable((String)request
                        .getAttribute("redirect-url"))
                .orElse("/");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            log.info("Email = {}", oAuth2User.getEmail());
            String accessToken = "Bearer " + jwtService.createAccessToken(oAuth2User.getEmail());
            String refreshToken = "Bearer "  + jwtService.createRefreshToken();

            log.info("accessToken = {}", accessToken);
            log.info("refreshToken = {}", refreshToken);
            if(oAuth2User.getRole() == Role.GUEST) {
                loginAsGuest(response, accessToken);
                // 회원가입 폼으로 들어가던가, 아니면
            } else {
                log.info("기존 가입 user = {}", oAuth2User.getRole().name());
                loginAsUser(response, oAuth2User, accessToken, refreshToken);
            // 기존에 가입이 되어있다면, redirect
            }
        } catch(Exception e) {
            throw e;
        }
    }

    private void loginAsGuest(HttpServletResponse response, String accessToken) throws IOException {
        response.addHeader(jwtService.getAccessHeader(), accessToken);
        jwtService.sendAccessAndRefreshToken(response, accessToken, null);
    }

    private void loginAsUser(HttpServletResponse response, CustomOAuth2User oAuth2User, String accessToken, String refreshToken) throws IOException {

        response.addHeader(jwtService.getAccessHeader(), accessToken);
        response.addHeader(jwtService.getRefreshHeader(), refreshToken);
        log.info("sendAccessToken");
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken.replace(JwtService.BEARER, ""));
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        log.info("updateRefreshToken");
        response.getWriter().write(objectMapper.writeValueAsString(new TokenDto(accessToken, refreshToken)));
        response.sendRedirect(makeRedirectUri(accessToken, refreshToken, null));
    }

    private String makeRedirectUri(String accessToken, String refreshToken, String redirectUrl) {
        log.info("redirect : refreshToken = {}", refreshToken);
        return UriComponentsBuilder.fromUriString("http://localhost:3000/login")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }
}
