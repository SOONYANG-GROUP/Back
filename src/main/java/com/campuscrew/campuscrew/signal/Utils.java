package com.campuscrew.campuscrew.signal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Message getObject(final String message) throws JsonProcessingException {
        return objectMapper.readValue(message, Message.class);
    }

    public static String getString(final Message message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }
}
