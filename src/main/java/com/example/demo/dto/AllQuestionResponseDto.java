package com.example.demo.dto;

import com.example.demo.model.Question;

import lombok.Getter;

@Getter
public class AllQuestionResponseDto {
    private Long questionId;
    private String title;
    private int likeCnt;
    // private String img;

    public AllQuestionResponseDto(Question question) {
        this.questionId = question.getId();
        this.title = question.getTitle();
        this.likeCnt = question.getLikeCnt();
    }
}
