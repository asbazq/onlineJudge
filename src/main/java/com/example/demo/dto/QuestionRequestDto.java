package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class QuestionRequestDto {
    private String title;
    private String content;
    private MultipartFile img;
}
