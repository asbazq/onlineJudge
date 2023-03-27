package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);
}
