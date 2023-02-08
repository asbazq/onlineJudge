package com.example.demo.dto;

import com.example.demo.model.Question;

import lombok.Getter;

@Getter
public class QuestionResponseDto {
    private Long questionId;
    private String title;
    private String content;
    private int likeCnt;
    private int TCCnt;
    private String img;

    public QuestionResponseDto(Question question) {
        this.questionId = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.likeCnt = question.getLikeCnt();
        this.TCCnt = question.getTCCnt();
    }
}
