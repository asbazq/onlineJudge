package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity
@Getter
public class PHUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problemHistory_id")
    private ProblemHistory problemHistory;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    public PHUsers(Long id, ProblemHistory problemHistory, Users users) {
        this.id = id;
        this.problemHistory = problemHistory;
        this.users = users;
    }

}
