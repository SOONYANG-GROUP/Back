package com.campuscrew.campuscrew.jwt;

import com.campuscrew.campuscrew.domain.user.User;
import com.campuscrew.campuscrew.dto.TokenDto;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import com.campuscrew.campuscrew.util.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request.getRequestURI() = {}", request.getRequestURI());
        String accessToken = jwtService.extractAccessToken(request)
                        .filter(jwtService::isTokenValid)
                .orElse(null);
        log.info("header에 accessToken이 있나? = {}", jwtService.extractAccessToken(request).orElse(null));
        log.info("accessToken이 유효한가? = {}", accessToken); // 유효하지 않으면 null 반환

        //        if (request.getRequestURI().equals(NO_CHECK_URL)) {
        //            filterChain.doFilter(request, response);
        //            return;
        //        }
        // 사용자 요청 헤더에서 RefreshToken 추출
        // -> RefreshToken이 없거나 유효하지 않다면(DB에 저장된 RefreshToken과 다르다면) null을 반환
        // 사용자의 요청 헤더에 RefreshToken이 있는 경우는, AccessToken이 만료되어 요청한 경우밖에 없다.
        // 따라서, 위의 경우를 제외하면 추출한 refreshToken은 모두 null
        String refreshToken =  jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        log.info("refreshToken = {}", refreshToken);
        // refreshToken 은 AccessToken 이 유효하다면 보내지 않는다.
        // refreshToken 이 온다면, AccessToken이 만료, DB 에서 이를 확인 하고 일치하면 재발급한다.
        if(refreshToken != null) {
            checkRefreshTokenAndIssueAccessToken(response, refreshToken);
            return;
        }

        if(refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }
    // refreshToken이 유효하지 않으면 AccessToken을 검사.
    // 1. 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어간다. >> 인증 성공
    // 2. 유효하지 않으면 403 error 발생
    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email-> userRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication))); // user 정보를 담아야 하는 구나
        filterChain.doFilter(request, response);
    }

    //인증 허가 메서드
    private void saveAuthentication(User user) {
        String password = user.getPassword(); //
        if(password == null) { // 소셜 로그인 일 경우 패스워드가 x
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetails = org.springframework.security.core.userdetails
                .User
                .builder()
                .username(user.getEmail())
                .password(password)
                .roles(user.getRole().name())
                .build();

        log.info("name = {}", user.getRole().name());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 1. refreshToken 이 존재 한다면, 해당 토큰을 가지고 있는 user 정보를 조회한다. 있다면 2번
    // 2. 이 user 에게 새로운 accessToken 과 refreshToken 을 발급
    private void checkRefreshTokenAndIssueAccessToken(HttpServletResponse response, String refreshToken) {
        log.info("checkRefreshToken = {}", refreshToken);
        userRepository.findByRefreshToken(refreshToken) // 1 refreshToken으로 못찾는다.
                .ifPresent(user -> {
                    log.info("user = {}", user.getEmail());
                    String reIssuedRefreshToken = reIssueRefreshToken(user);
                    String accessToken = jwtService.createAccessToken(user.getEmail());
                    jwtService.sendAccessAndRefreshToken(response,
                            accessToken,
                            reIssuedRefreshToken);
                    try {
                        String jsonbody = objectMapper.writeValueAsString(new TokenDto(accessToken, reIssuedRefreshToken));
                        response.getWriter().write(jsonbody);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    // 재발행한 RefreshToken을 user정보에 업데이트
    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        log.info("reIssueToken = {}", reIssuedRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
    }
}
