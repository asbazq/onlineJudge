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
import org.springframework.data.domain.Pageable;

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
        MockitoAnnotations.initMocks(this);
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

        Page<Question> questionPage = mock(Page.class);
        when(questionPage.stream()).thenReturn(new ArrayList<Question>().stream());
        when(questionRepository.findAll(any(Pageable.class))).thenReturn(questionPage);

        // 테스트할 메서드 호출
        List<AllQuestionResponseDto> responseDtos = questionService.getquestions(page, size, sortby);

        // 결과 검증
        assert responseDtos.isEmpty();
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
        List<ProblemHistory> problemHistories = new ArrayList<>();
        problemHistories.add(problemHistory);
        when(problemHistoryRepository.findByQuestion(question)).thenReturn(problemHistories);

        // 테스트할 메서드 호출
        List<ProblemHistoryDto> dtos = questionService.getQuestionHistory(questionId);

        // 결과 검증
        assert !dtos.isEmpty();
    }
}
