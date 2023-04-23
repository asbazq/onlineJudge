package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    // 검색
    @GetMapping("api/search")
    public ResponseEntity<List<QuestionResponseDto>> search(@RequestParam("query") String query) {
        // @ReuqestParam 어노테이션은 HttpServletRequest 객체와 같은 역할
        List<QuestionResponseDto> dto = searchService.search(query);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
