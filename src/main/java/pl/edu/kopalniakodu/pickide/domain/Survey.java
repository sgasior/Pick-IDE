package pl.edu.kopalniakodu.pickide.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.PreferedCriteria;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.PreferedIDE;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.ProgrammingSkill;
import pl.edu.kopalniakodu.pickide.domain.validator.NonWhitespace;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please enter survey name")
    @NonNull
    @NonWhitespace(message = "Your survey name cannot contain whitespaces")
    private String surveyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "survey", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Criteria> criterias = new ArrayList<>();


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "survey", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Alternative> alternatives = new ArrayList<>();


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "survey", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Answer> answers = new ArrayList<>();


    @Transient
    private ProgrammingSkill programmingSkill;

    @Transient
    private PreferedCriteria preferedCriteria;

    @Transient
    private PreferedIDE preferedIDE;

    public Survey() {

    }

    public Survey(String surveyName, ProgrammingSkill programmingSkill) {
        this.surveyName = surveyName;
        this.programmingSkill = programmingSkill;
        //addPreferedCriteriasAndAlternatives();
    }

    public Survey(String surveyName, User user) {
        this.surveyName = surveyName;
        this.user = user;
    }

    public Survey(String surveyName, User user, ProgrammingSkill programmingSkill) {
        this.surveyName = surveyName;
        this.user = user;
        this.programmingSkill = programmingSkill;
        // addPreferedCriteriasAndAlternatives();
    }

    private void addPreferedCriteriasAndAlternatives() {
        if (programmingSkill.getResponse().equals(ProgrammingSkill.BEGINNER.getResponse())) {
            List<String> preferedCriterias = PreferedCriteria.BEGINNER.getReponse();
            List<String> preferedAlternatives = PreferedIDE.BEGINNER.getReponse();
            addDefaultCriterias(preferedCriterias);
            addDefaultAlternatives(preferedAlternatives);

        } else if ((programmingSkill.getResponse().equals(ProgrammingSkill.MID_EXP.getResponse()))) {

            List<String> preferedCriterias = PreferedCriteria.MID_EXP.getReponse();
            List<String> preferedAlternatives = PreferedIDE.MID_EXP.getReponse();
            addDefaultCriterias(preferedCriterias);
            addDefaultAlternatives(preferedAlternatives);
        } else {
            List<String> preferedCriterias = PreferedCriteria.PRO_EXP.getReponse();
            List<String> preferedAlternatives = PreferedIDE.PRO_EXP.getReponse();
            addDefaultCriterias(preferedCriterias);
            addDefaultAlternatives(preferedAlternatives);
        }
    }


    private void addDefaultCriterias(List<String> preferedCriterias) {
        for (String criteriaName : preferedCriterias) {
            addCriteria(new Criteria(criteriaName, this));
        }
    }

    private void addDefaultAlternatives(List<String> preferedCriterias) {
        for (String alternativeName : preferedCriterias) {
            addAlternative(new Alternative(alternativeName, this));
        }
    }


    public void addCriteria(Criteria criteria) {
        criterias.add(criteria);
        criteria.setSurvey(this);
    }

    public void addAlternative(Alternative alternative) {
        alternatives.add(alternative);
        alternative.setSurvey(this);
    }


    public void addAnswwer(Answer answer) {
        answers.add(answer);
        answer.setSurvey(this);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", surveyName='" + surveyName + '\'' +
                '}';
    }
}


