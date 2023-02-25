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

    public enum LANGUAGE {
        JAVA("java"), PYTHON("python"), C_lANGUAGE("c"), CPP_LAGUAGE("c++");

        private final String label;

        LANGUAGE(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private int likeCnt;

    // @Column
    // private String img;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Column
    private LANGUAGE language;

    @Builder.Default // @Builder 는 초기화를 무시, 초기화를 위해 @Builder.Default or final
    @OneToMany(mappedBy = "question")
    private List<TestCase> testCases = new ArrayList<>();

    public Question(Long id, String title, String content, int likeCnt, Users users, LANGUAGE language) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likeCnt = likeCnt;
        this.users = users;
        this.language = language;
    }
}
