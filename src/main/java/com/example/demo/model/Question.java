package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
// @Table(indexes = {@Index(name = "question_idx", columnList = "title, content")})
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String restrictions;

    @Column
    private String example;

    // orphanRemoval = true 부모 엔티티가 삭제되면 자식 엔티티도 삭제
    @OneToMany(mappedBy = "question", orphanRemoval = true)
    @Builder.Default
    private List<InputOutput> inputOutputs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problemHistroy_id")
    private ProblemHistory problemHistory;

    public Question(Long id, String title, String content, String restrictions, String example, Users users) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.restrictions = restrictions;
        this.example = example;
        this.users = users;
    }
}
