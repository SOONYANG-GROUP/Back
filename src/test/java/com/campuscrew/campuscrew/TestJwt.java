package com.campuscrew.campuscrew;

import com.campuscrew.campuscrew.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class TestJwt {
    @Test
    void objectMapper() throws JsonProcessingException {

    }

    @Test
    void test() {
//        project.getRecruitmentDate().compareTo(LocalDateTime.now()) < 0)
        LocalDateTime now = LocalDateTime.now();
        int i = now.compareTo(LocalDateTime.of(2023, 5, 1, 11, 20, 1));

        System.out.println(i);
    }

    @Test
    void create() {

    }
}
