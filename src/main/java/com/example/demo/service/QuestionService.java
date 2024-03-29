package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.AllQuestionResponseDto;
import com.example.demo.dto.QuestionRequestDto;
import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.dto.IORequestDto;
import com.example.demo.dto.ProblemHistoryDto;
import com.example.demo.model.Question;
import com.example.demo.repository.InputOutputRepository;
import com.example.demo.repository.ProblemHistoryRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.security.UserDetailsImpl;


import com.example.demo.model.InputOutput;
import com.example.demo.model.ProblemHistory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InputOutputRepository inputOutputRepository;
    private final ProblemHistoryRepository problemHistoryRepository;

    // 문제 작성
    public void createQuestion(QuestionRequestDto requestDto, UserDetailsImpl userDetailsImpl) {

        Question question = Question.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .restrictions(requestDto.getRestrictions())
                .example(requestDto.getExample())
                .users(userDetailsImpl.getUsers())
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
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );
        return new QuestionResponseDto(question);
    }

    // 문제 전체 조회
    public List<AllQuestionResponseDto> getquestions(int page, int size, String sortby) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, sortby));
        Page<Question> questionPage = questionRepository.findAll(pageable);
        return questionPage.stream()
        .map(question -> new AllQuestionResponseDto(question))
                .collect(Collectors.toList());
    }

    //문제 삭제
    @Transactional
    public void deleteque(Long questionId, UserDetailsImpl userDetailsImpl) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if (question.getUsers().getId().equals(userDetailsImpl.getUsers().getId())) {
            questionRepository.deleteById(questionId);
        } else {

            throw new CustomException(ErrorCode.INVALID_AUTHORITY);
        }
    }

    // 성공한 문제 조회
    public List<ProblemHistoryDto> getQuestionHistory(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
            ()-> new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );

        List<ProblemHistory> problemHistory = problemHistoryRepository.findByQuestion(question);
        List<ProblemHistoryDto> dtos = new ArrayList<>();
        for (ProblemHistory pHistory : problemHistory) {
            ProblemHistoryDto dto = new ProblemHistoryDto(pHistory);
            dtos.add(dto);
        }

        return dtos;
    }
}
