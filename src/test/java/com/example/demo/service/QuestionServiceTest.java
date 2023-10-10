package com.example.demo.service;

import com.example.demo.dto.AllQuestionResponseDto;
import com.example.demo.dto.QuestionRequestDto;
import com.example.demo.dto.QuestionResponseDto;
import com.example.demo.dto.IORequestDto;
import com.example.demo.dto.ProblemHistoryDto;
import com.example.demo.model.*;
import com.example.demo.repository.InputOutputRepository;
import com.example.demo.repository.ProblemHistoryRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

    @Mock // mock 객체를 생성
    private QuestionRepository questionRepository;

    @Mock
    private InputOutputRepository inputOutputRepository;

    @Mock
    private ProblemHistoryRepository problemHistoryRepository;

    @InjectMocks // 클래스의 인스턴스를 생성
    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        // @Mock와 @InjectMocks이 붙은 필드를 인식, Mockito가 목 객체를 초기화하고 필드에 할당
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateQuestion() {
        // Mock 데이터 설정
        Users users = new Users("test", "1234", "test@test.com", Role.ROLE_ADMIN);
        users.setId(1L); // 사용자 ID 설정
        UserDetailsImpl userDetails = new UserDetailsImpl(users);

        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setTitle("Sample Title");
        requestDto.setContent("Sample Content");
        requestDto.setRestrictions("Sample Restrictions");
        requestDto.setExample("Sample Example");

        // 테스트할 메서드 호출
        questionService.createQuestion(requestDto, userDetails);

        // Mockito를 사용하여 questionRepository.save가 한 번 호출되었는지 확인
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    public void testPutIO() {
        // Mock 데이터 설정
        Long questionId = 1L;
        IORequestDto requestDto = new IORequestDto();
        requestDto.setInput("Sample Input");
        requestDto.setOutput("Sample Output");

        // Question 객체를 생성하고 questionRepository.findById(questionId)가 호출될 때 이 객체를 반환
        // 실제 데이터베이스에 접근하지 않고 모의(Mock) 객체를 사용
        Question question = new Question();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // 테스트할 메서드 호출
        questionService.putio(questionId, requestDto);

        // Mockito를 사용하여 inputOutputRepository.save가 한 번 호출되었는지 확인
        verify(inputOutputRepository, times(1)).save(any(InputOutput.class));
    }

    @Test
    public void testGetQuestion() {
        // Mock 데이터 설정
        Long questionId = 1L;
        Question question = new Question();
        question.setTitle("Sample Title");
        question.setContent("Sample Content");
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // 테스트할 메서드 호출
        QuestionResponseDto responseDto = questionService.getQuestion(questionId);

        // 결과 검증
        assert responseDto.getTitle().equals("Sample Title");
        assert responseDto.getContent().equals("Sample Content");
    }

    @Test
    public void testGetQuestions() {
        // Mock 데이터 설정
        int page = 1;
        int size = 10;
        String sortby = "title";
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, sortby));
        List<Question> fakeQuestions = new ArrayList<>();
        // 가짜 질문 데이터 생성
        for (int i = 1; i <= size; i++) {
            Question question = new Question();
            question.setTitle("Question " + i);
            fakeQuestions.add(question);
        }

        Page<Question> fakeQuestionPage = new PageImpl<>(fakeQuestions, pageable, fakeQuestions.size());

        // 목(Mock) 설정
        when(questionRepository.findAll(pageable)).thenReturn(fakeQuestionPage);

        // 테스트 메서드 호출
        List<AllQuestionResponseDto> result = questionService.getquestions(page, size, sortby);

        // 결과 검증
        // 페이지 크기와 결과 크기가 일치하는지 확인
        assert result.size() == size;

        // 결과 데이터가 예상과 일치하는지 확인
        assert result.get(0).getTitle().equals("Question 1");
        assert result.get(1).getTitle().equals("Question 2");
        assert result.get(2).getTitle().equals("Question 3");
    }
    

    @Test
    public void testDeleteque() {
        // Mock 데이터 설정
        Long questionId = 1L;
        Users users = new Users("test", "1234", "test@test.com", Role.ROLE_ADMIN);
        users.setId(1L); // 사용자 ID 설정
        UserDetailsImpl userDetails = new UserDetailsImpl(users);

        Question question = new Question();
        question.setUsers(users);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // 테스트할 메서드 호출
        questionService.deleteque(questionId, userDetails);

        // Mockito를 사용하여 questionRepository.deleteById가 한 번 호출되었는지 확인
        verify(questionRepository, times(1)).deleteById(questionId);
    }

    @Test
    public void testGetQuestionHistory() {
        // Mock 데이터 설정
        Long questionId = 1L;
        Question question = new Question();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        ProblemHistory problemHistory = new ProblemHistory();
        Users users = new Users("test", "1234", "test@test.com", Role.ROLE_ADMIN);
        problemHistory.setUsers(users);
        problemHistory.setCode("test code");
        List<ProblemHistory> problemHistories = new ArrayList<>();
        problemHistories.add(problemHistory);
        when(problemHistoryRepository.findByQuestion(question)).thenReturn(problemHistories);

        // 테스트할 메서드 호출
        List<ProblemHistoryDto> dtos = questionService.getQuestionHistory(questionId);

        // 결과 검증
        assert !dtos.isEmpty();
        assert dtos.get(0).getCode().equals("test code");
    }
}
