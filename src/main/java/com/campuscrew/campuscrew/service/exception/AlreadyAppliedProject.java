package com.campuscrew.campuscrew.service.exception;

public class AlreadyAppliedProject extends RuntimeException{
    public AlreadyAppliedProject(String message) {
        super(message);
    }
}
