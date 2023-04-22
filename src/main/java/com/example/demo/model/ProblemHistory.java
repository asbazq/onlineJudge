package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
public class ProblemHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "problemHistory")
    @Column(name = "users_id")
    private List<Users> users = new ArrayList<>();

    @Column
    private String code;

    @Builder.Default
    @OneToMany(mappedBy = "problemHistory")
    private List<Question> question = new ArrayList<>();
}
