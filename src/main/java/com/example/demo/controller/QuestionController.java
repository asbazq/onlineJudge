package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AllQuestionResponseDto;
import com.example.demo.dto.QuestionRequestDto;
import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private QuestionService questionService;

    // 문제 작성
    public ResponseEntity<Void> createQuestion(@ModelAttribute QuestionRequestDto requestDto) {
        questionService.createQuestion(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 문제 상세 조회
    public ResponseEntity<QuestionResponseDto> getquestion(@PathVariable Long id) {
        QuestionResponseDto dto = questionService.getQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 문제 전체 조회
    @GetMapping("api/questions")
    public ResponseEntity<List<AllQuestionResponseDto>> getquestions(@RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortby) {
        List<AllQuestionResponseDto> dtos = questionService.getquestions(page, size, sortby);

        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }

}
