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
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String criteriaName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "criteria", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<AnswerCriteria> answerCriteria = new HashSet<>();

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "criteria", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<AnswerAlternative> answerAlternative = new HashSet<>();

    public Criteria() {

    }

    public Criteria(String criteriaName) {
        this.criteriaName = criteriaName;
    }


    public Criteria(String criteriaName, Survey survey) {
        this.criteriaName = criteriaName;
        this.survey = survey;
    }


    @Override
    public String toString() {
        return "Criteria{" +
                "id=" + id +
                ", criteriaName='" + criteriaName + '\'' +
                '}';
    }
}
