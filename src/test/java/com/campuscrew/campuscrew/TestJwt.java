package com.campuscrew.campuscrew;

import com.campuscrew.campuscrew.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

public class TestJwt {

    @Test
    void objectMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(new TokenDto("a", "b"));
        System.out.println(s);
    }
    @Test
    void test() {
        String s = UriComponentsBuilder.fromUriString("http://localhost:3000")
                .path("/ddd")
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        System.out.println(s);
    }
}
