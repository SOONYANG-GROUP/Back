package com.campuscrew.campuscrew.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("로그인 실패");
        log.info("소셜 로그인에 실패, {}", exception.getMessage());
    }
}

//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTY3OTU0ODIzMCwiZW1haWwiOiJkNzY2MTgzNC02N2I0LTQ4YTItYTRhOS0xMzc3NWQzZmNmOGJAZ29vZ2xlLmNvbSJ9.vLFUL6HR1_-GWTe8kwcRulfCvfMJOgzoTkN9ZFAhKK8-1b0t0zPP72sd6S9p0tJIVUe1EEEjcp1Cdw76bhEAmw
