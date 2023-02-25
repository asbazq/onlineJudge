package com.example.demo.dto;

import lombok.Getter;

@Getter
public class InputRequestDto {
    
    private String input;

    public InputRequestDto(String input) {
        this.input = input;
    }
}
