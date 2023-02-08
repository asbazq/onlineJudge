package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private int likeCnt;

    @Column
    private int TCCnt; // TestCase Count

    @Column
    private String img;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder.Default // @Builder 는 초기화를 무시, 초기화를 위해 @Builder.Default or final
    @OneToMany(mappedBy = "question")
    private List<TestCase> testCases = new ArrayList<>();
}
