package com.campuscrew.campuscrew.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}
