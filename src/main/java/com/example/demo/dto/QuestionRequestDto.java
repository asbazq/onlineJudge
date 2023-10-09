package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDto {
    private String title;
    private String content;
    private String restrictions;
    private String example;
    // private List<String> input;
    // private List<String> output;
}
