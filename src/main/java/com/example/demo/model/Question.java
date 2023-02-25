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
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "question")
    private List<InputOutput> inputOutput;

    @ManyToOne
    private Users users;

    @Builder.Default // @Builder 는 초기화를 무시, 초기화를 위해 @Builder.Default or final
    @OneToMany(mappedBy = "question")
    private List<TestCase> testCases = new ArrayList<>();

    public Question(Long id, String title, String content, Users users) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.users = users;
    }
}
