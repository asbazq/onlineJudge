package com.example.demo.reposotory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Question;
import com.example.demo.model.Question.LANGUAGE;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);
}
