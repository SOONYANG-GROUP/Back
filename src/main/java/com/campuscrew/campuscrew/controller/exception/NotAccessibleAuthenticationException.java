package com.campuscrew.campuscrew.controller.exception;

public class NotAccessibleAuthenticationException extends RuntimeException{
    public NotAccessibleAuthenticationException(String message) {
        super(message);
    }
}
