package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ProblemHistory;
import java.util.List;
import com.example.demo.model.Question;


public interface ProblemHistoryRepository extends JpaRepository<ProblemHistory, Long> {
    List<ProblemHistory> findByQuestion(Question question);
}
