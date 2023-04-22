package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> search(String query) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Question> questions = questionRepository.searchByTitleOrContentContaining(query);
        List<QuestionResponseDto> responseDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionResponseDto dto = new QuestionResponseDto(question);
            responseDtos.add(dto);
        }
        stopWatch.stop();
        log.info("검색 수행시간 >> {}", stopWatch.getTotalTimeSeconds());
        return responseDtos;
    }
}
