package com.campuscrew.campuscrew.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    @PostMapping("/add")
    public String addBoard() {
        return null;
    }
}
