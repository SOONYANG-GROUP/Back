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
        String str1 = "Hello";
        String str2 = str1;

        str1 = str1 + "hh";

        System.out.println(str1);
        System.out.println(str2);

    }

    @Test
    void create() {

    }
}
