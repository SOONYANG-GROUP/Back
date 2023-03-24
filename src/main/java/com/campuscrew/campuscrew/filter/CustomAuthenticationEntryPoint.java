package com.campuscrew.campuscrew.filter;

import com.campuscrew.campuscrew.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("request.url = {}", request.getRequestURI());
        TokenDto tokenDto = new TokenDto(request.getHeader("Authorization"), request.getHeader("Authorizationrefresh"));
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(tokenDto);
        log.info(authException.getMessage());
        response.getWriter().write(s);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
