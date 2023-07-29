package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.ExecutedCode;

import io.lettuce.core.dynamic.annotation.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ExecutedCodeRepository extends JpaRepository<ExecutedCode, Long> {
    List<ExecutedCode> findByQuestionAndUsersAndCode(Long question, String users, int code);

    @Modifying
    @Query("DELETE FROM ExecutedCode ec WHERE ec.createAt < :expirationDate")
    void dedeleteByCreateAtBefore(@Param("expirationDate") LocalDateTime expirationDate);
}
