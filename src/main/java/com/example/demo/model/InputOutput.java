package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InputOutput implements Serializable { // 영속성 컨텍스트는 엔티티의 PK를 사용해서 엔티티를 관리, 그러나 pk가 아닌 unique필드를 사용하기 때문에 직렬화

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", unique = false)
    private Question question;

    @Column(name = "input")
    private String input;

    @Column(name = "output")
    private String output;

    
}
