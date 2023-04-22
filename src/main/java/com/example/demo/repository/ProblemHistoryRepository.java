package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ProblemHistory;

public interface ProblemHistoryRepository extends JpaRepository<ProblemHistory, Long> {

}
