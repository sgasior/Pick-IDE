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
public class Alternative {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alternativeName;

    private String alternativeDescription;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "alternative", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<AnswerAlternative> answerAlternatives = new HashSet<>();

    public Alternative() {
    }


    public Alternative(String alternativeName, Survey survey) {
        this.alternativeName = alternativeName;
        this.survey = survey;
    }

    public Alternative(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public Alternative(String alternativeName, String alternativeDescription) {
        this.alternativeName = alternativeName;
        this.alternativeDescription = alternativeDescription;
    }


    @Override
    public String toString() {
        return "Alternative{" +
                "id=" + id +
                ", alternativeName='" + alternativeName + '\'' +
                ", alternativeDescription='" + alternativeDescription + '\'' +
                '}';
    }
}
