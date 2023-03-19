package com.campuscrew.campuscrew.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("POST", "GET", "DELETE", "PATCH", "PUT")
                .allowCredentials(true);
    }
}
