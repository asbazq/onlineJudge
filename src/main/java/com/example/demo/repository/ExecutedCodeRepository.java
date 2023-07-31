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

    @Modifying // 데이터베이스의 상태를 변경하는 메서드에만 사용, SELECT 쿼리에는 @Query 어노테이션만 사용
    @Query("DELETE FROM ExecutedCode ec WHERE ec.createdAt < :expirationDate")
    void dedeleteByCreateAtBefore(@Param("expirationDate") LocalDateTime expirationDate);
}
