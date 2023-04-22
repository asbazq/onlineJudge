package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.InputRequestDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.AnswerCheckService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnswerCheckController {

    private final AnswerCheckService answerCheckService;

    // 클라이언트 정답 제출
    @PostMapping("api/submission/{questionId}")
    public ResponseEntity<String> submission(@RequestBody InputRequestDto requestDto,
            @PathVariable Long questionId,
            @AuthenticationPrincipal UserDetailsImpl UserDetailsImpl)
            throws SQLException, IOException {
        String result = answerCheckService.submission(requestDto, questionId, UserDetailsImpl);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
