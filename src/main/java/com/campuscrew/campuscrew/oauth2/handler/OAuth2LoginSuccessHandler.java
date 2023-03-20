package com.campuscrew.campuscrew.oauth2.handler;

import com.campuscrew.campuscrew.domain.Role;
import com.campuscrew.campuscrew.domain.User;
import com.campuscrew.campuscrew.jwt.JwtService;
import com.campuscrew.campuscrew.oauth2.CustomOAuth2User;
import com.campuscrew.campuscrew.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 성공");

        String attribute = Optional.ofNullable((String)request.getAttribute("redirect-url"))
                .orElse("/");
        log.info("attribute = {}", attribute);
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            String accessToken = "Bearer " + jwtService.createAccessToken(oAuth2User.getEmail());
            log.info("accessToken = {}", accessToken);
            if(oAuth2User.getRole() == Role.GUEST) {
                response.addHeader(jwtService.getAccessHeader(), accessToken);
                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
            // 회원가입 폼으로 들어가던가, 아니면
            } else {
                log.info("기존 가입 user = {}", oAuth2User.getRole().name());
                loginSuccess(response, oAuth2User);
            // 기존에 가입이 되어있다면, redirect
            }
            response.sendRedirect("http://localhost:3000");
        } catch(Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) {
        String accessToken = "Bearer " + jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = "Bearer "  + jwtService.createRefreshToken();

        response.addHeader(jwtService.getAccessHeader(), accessToken);
        response.addHeader(jwtService.getRefreshHeader(), refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
