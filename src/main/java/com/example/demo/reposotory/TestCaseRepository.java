package com.example.demo.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TestCase;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    
}
