package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.InputRequestDto;
import com.example.demo.service.AnswerCheckService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnswerCheckController {

    private final AnswerCheckService answerCheckService;

    @PostMapping("api/question/submission/{questionId}")
    public ResponseEntity<String> submission(@RequestBody InputRequestDto requestDto, @PathVariable Long questionId) {
        String result = answerCheckService.submission(requestDto, questionId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
