package com.campuscrew.campuscrew.signal;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebRtcConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "ws/chat")
                .setAllowedOrigins("*")
                .withSockJS();
    }
    @Bean
    public WebSocketHandler signalingHandler() {
        return new SignalingHandler();
    }
}
