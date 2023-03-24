package com.campuscrew.campuscrew.controller.exception;

public class RequiredLoginStateException extends RuntimeException{
    public RequiredLoginStateException(String message) {
        super(message);
    }
}
