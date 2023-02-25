package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.InputRequestDto;
import com.example.demo.model.InputOutput;
import com.example.demo.model.Question;
import com.example.demo.reposotory.InputOutputRepository;
import com.example.demo.reposotory.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class AnswerCheckService {

    private final QuestionRepository questionRepository;
    private final InputOutputRepository inputOutputRepository;

    public void inputQuestion(InputRequestDto requestDto, Long questionId) {

        InputOutput inputOutput = inputOutputRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.EMPTY_CONTENT));
        for (int i = 0; i < 5; i++) {

        }
    }

}
