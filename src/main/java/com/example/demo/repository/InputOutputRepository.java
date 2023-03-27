package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.InputOutput;



public interface InputOutputRepository extends JpaRepository<InputOutput, Long> {
    Optional<InputOutput> findById(Long id);
    List<InputOutput> findByQuestionId(Long questionId);
}
