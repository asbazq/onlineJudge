package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // @RequestBody 에서 DTO 변환시 기본생성자를 필요
public class InputRequestDto {
    
    private String input;
    private String lang;

    public InputRequestDto(String input, String lang) {
        this.input = input;
        this.lang = lang;
    }
}
