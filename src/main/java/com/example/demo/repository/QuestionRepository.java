package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);

    @Query("SELECT p FROM Question p WHERE p.title LIKE %:query% OR p.content LIKE %:query%")
    List<Question> searchByTitleOrContentContaining(@Param("query") String query);
}
