package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> search(String query) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Question> questions = questionRepository.searchByTitleOrContentContaining(query);
        log.info("검색 수행시간 >> {}", stopWatch.getTotalTimeSeconds());
        return questions.stream()
        .map(question -> new QuestionResponseDto(question))
                .collect(Collectors.toList());

    }
}
