package com.campuscrew.campuscrew.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.campuscrew.campuscrew.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}") // 1000 * 2000
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String USER_ID = "user_id";
    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    public static final String EMAIL_CLAIM = "email";

    public static final String NICKNAME_CLAIM = "nickname";
    public static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    // accessToken : 사용자 인증을 진행 한 후 발행되는 토큰이다.
    // email -> UserDto
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + + accessTokenExpirationPeriod)) // 토큰 생성
                .withClaim(EMAIL_CLAIM, email) // payload에 담기는 내용
                .sign(Algorithm.HMAC512(secretKey));
    }
    // refreshToken : accessToken의 재발급을 위한 token, client에 저장되지 않아야 하는 것
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        setAccessTokenHeader(response, accessToken);
        setRefreshHeader(response, refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
        log.info("accessToken = {}", accessToken);
        log.info("refreshToken = {}", refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 ok");
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    public void setRefreshHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    // 인증이 완료된 클라이언트의 정보를 확인하기 위해서 token 을 추출
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    // 인증이 완료된 클라이언트의 정보를 확인하기 위해서 순수 token 을 추출
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }
    // accessToken 을 복호화하기 위해서 jwt.require() (jwt 토큰 검증기)를 돌린다
    //
    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());

        } catch (Exception e) {
            log.error("엑세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> {
                            log.info("update refresh Token = {}", refreshToken);
                            user.updateRefreshToken(refreshToken);
                            userRepository.saveAndFlush(user);
                        },
                        () -> new Exception("일치하는 회원이 없습니다."));
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰. {}", e.getMessage());
            return false;
        }
    }


    public String createAccessToken(String email, Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + + accessTokenExpirationPeriod)) // 토큰 생성
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(USER_ID, id)// payload에 담기는 내용
                .sign(Algorithm.HMAC512(secretKey));
    }
}
