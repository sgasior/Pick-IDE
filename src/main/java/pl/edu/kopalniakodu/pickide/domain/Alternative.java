package pl.edu.kopalniakodu.pickide.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Alternative {


    @Id
    @GeneratedValue
    private Long id;

    public Alternative() {
    }

    private String alternativeName;

    private double alternativeWeight;

    private String alternativeDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;


    public Alternative(String alternativeName, Survey survey) {
        this.alternativeName = alternativeName;
        this.survey = survey;
    }

    public Alternative(String alternativeName, double alternativeWeight, String alternativeDescription) {
        this.alternativeName = alternativeName;
        this.alternativeWeight = alternativeWeight;
        this.alternativeDescription = alternativeDescription;
    }

    @Override
    public String toString() {
        return "Alternative{" +
                "id=" + id +
                ", alternativeName='" + alternativeName + '\'' +
                ", alternativeWeight=" + alternativeWeight +
                ", alternativeDescription='" + alternativeDescription + '\'' +
                '}';
    }
}
