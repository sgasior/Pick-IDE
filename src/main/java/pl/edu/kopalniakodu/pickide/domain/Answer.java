package pl.edu.kopalniakodu.pickide.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Answer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "answer", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<AnswerCriteria> answerCriteria = new HashSet<>();


    public Answer() {

    }


}


