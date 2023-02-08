package com.example.demo.reposotory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllBy(PageRequest pageRequest);

}
