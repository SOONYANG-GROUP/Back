package com.campuscrew.campuscrew;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

public class TestJwt {
    @Test
    void test() {
        String s = UriComponentsBuilder.fromUriString("http://localhost:3000")
                .path("/ddd")
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        System.out.println(s);
    }
}
