package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> search(String query) {
        List<Question> questions = questionRepository.searchByTitleOrContentContaining(query);
        List<QuestionResponseDto> responseDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionResponseDto dto = new QuestionResponseDto(question);
            responseDtos.add(dto);
        }
        return responseDtos;
    }
}
