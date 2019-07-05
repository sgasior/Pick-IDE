package pl.edu.kopalniakodu.pickide.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Criteria {

    @Id
    @GeneratedValue
    private Long id;

    private String criteriaName;

    private double criteriaWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public Criteria() {

    }

    public Criteria(String criteriaName, Survey survey) {
        this.criteriaName = criteriaName;
        this.survey = survey;
    }

    public Criteria(String criteriaName, double criteriaWeight, Survey survey) {
        this.criteriaName = criteriaName;
        this.criteriaWeight = criteriaWeight;
        this.survey = survey;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "id=" + id +
                ", criteriaName='" + criteriaName + '\'' +
                ", criteriaWeight=" + criteriaWeight +
                '}';
    }
}
