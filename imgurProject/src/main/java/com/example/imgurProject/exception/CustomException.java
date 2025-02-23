package com.example.imgurProject.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{

    private String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
