package com.campuscrew.campuscrew.login.handler;

import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.TokenDto;
import com.campuscrew.campuscrew.jwt.JwtService;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractUsername(authentication);
        String accessToken =  jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    user.updateRefreshToken(refreshToken);
                    userRepository.saveAndFlush(user);
                });
        log.info("로그인 성공. 이메일: {}", email);
        log.info("로그인 성공. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
        response.getWriter().write(objectMapper.writeValueAsString(new TokenDto("Bearer "+ accessToken,"Bearer "+  refreshToken)));
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

//
//    private String makeRedirectUri(String accessToken, String refreshToken, String redirectUrl) {
//        log.info("redirect : refreshToken = {}", refreshToken);
//        return UriComponentsBuilder.fromUriString("http://localhost:8080/exlogin")
//                .queryParam("accessToken", accessToken)
//                .queryParam("refreshToken", refreshToken)
//                .encode(StandardCharsets.UTF_8)
//                .toUriString();
//    }

}
