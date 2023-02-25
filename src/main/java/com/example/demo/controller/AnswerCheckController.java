package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.InputRequestDto;
import com.example.demo.service.AnswerCheckService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnswerCheckController {

    private AnswerCheckService answerCheckService;

    @PostMapping("api/question/input")
    public ResponseEntity<Void> inputQuestion(@RequestBody InputRequestDto requestDto, Long questionId) {
        answerCheckService.inputQuestion(requestDto, questionId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("greeting")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("hello world");
    }
}
