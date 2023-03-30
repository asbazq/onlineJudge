package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.AllQuestionResponseDto;
import com.example.demo.dto.QuestionRequestDto;
import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.dto.IORequestDto;
import com.example.demo.model.Question;
import com.example.demo.repository.InputOutputRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.model.InputOutput;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InputOutputRepository inputOutputRepository;

    // 문제 작성
    public void createQuestion(QuestionRequestDto requestDto) {
        Question question = Question.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        questionRepository.save(question);
    }

    // 입출력 작성
    @Transactional
    public void putio(Long questionId, IORequestDto requestDto) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        InputOutput inputOutput = new InputOutput();
        inputOutput.setInput(requestDto.getInput());
        inputOutput.setOutput(requestDto.getOutput());
        inputOutput.setQuestion(question);

        inputOutputRepository.save(inputOutput);

    }


    // 문제 상세 조회
    public QuestionResponseDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        return new QuestionResponseDto(question);
    }

    // 문제 전체 조회
    public List<AllQuestionResponseDto> getquestions(int page, int size, String sortby) {

        page = page - 1;
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortby);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Question> questionPage = questionRepository.findAll(pageRequest);

        List<AllQuestionResponseDto> questionResponseDtos = new ArrayList<>();

        for (Question question : questionPage) {
            AllQuestionResponseDto responseDto = new AllQuestionResponseDto(question);
            questionResponseDtos.add(responseDto);
        }

        return questionResponseDtos;
    }

}
