package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CustomException extends RuntimeException{
    private final com.example.demo.Exception.ErrorCode errorCode;
}