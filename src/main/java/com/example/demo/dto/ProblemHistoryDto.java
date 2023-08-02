package com.example.demo.dto;

import com.example.demo.model.ProblemHistory;

import lombok.Getter;

@Getter
public class ProblemHistoryDto {
    String code;
    String username;

    public ProblemHistoryDto(ProblemHistory problemHistory) {
        this.code = problemHistory.getCode();
        this.username = problemHistory.getUsers().getUsername();
    }
}
