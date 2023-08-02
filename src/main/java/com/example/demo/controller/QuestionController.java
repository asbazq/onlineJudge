package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AllQuestionResponseDto;
import com.example.demo.dto.QuestionRequestDto;
import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.QuestionService;
import com.example.demo.dto.IORequestDto;
import com.example.demo.dto.ProblemHistoryDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 문제 작성
    @PostMapping("api/input")
    public ResponseEntity<Void> createQuestion(@RequestBody QuestionRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        questionService.createQuestion(requestDto, userDetailsImpl);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //문제 삭제
    @DeleteMapping("api/delete/{questionId}")
    public ResponseEntity<Void> deleteque(@PathVariable Long questionId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        questionService.deleteque(questionId, userDetailsImpl);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 입출력 작성
    @PostMapping("api/input/{questionId}")
    public ResponseEntity<Void> putio(@PathVariable Long questionId, @RequestBody IORequestDto requestDto) {
        questionService.putio(questionId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 문제 상세 조회
    @GetMapping("api/question/{id}")
    public ResponseEntity<QuestionResponseDto> getquestion(@PathVariable Long id) {
        QuestionResponseDto dto = questionService.getQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 문제 전체 조회
    @GetMapping("api/question")
    public ResponseEntity<List<AllQuestionResponseDto>> getquestions(@RequestParam int page,
                                                                    @RequestParam int size,
                                                                    @RequestParam String sortby) {
        List<AllQuestionResponseDto> dtos = questionService.getquestions(page, size, sortby);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }
    
    @GetMapping("api/question/{id}/history")
    public ResponseEntity<List<ProblemHistoryDto>> getQuestionHistory(@PathVariable Long id) {
        List<ProblemHistoryDto> dto = questionService.getQuestionHistory(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
