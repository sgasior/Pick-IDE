package pl.edu.kopalniakodu.pickide.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.edu.kopalniakodu.pickide.domain.util.ProgrammingSkill;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String surveyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    List<Criteria> criterias;
//
//    List<Alternative> alternatives;

    @Enumerated(EnumType.STRING)
    private ProgrammingSkill programmingSkill;

    public Survey() {

    }

    public Survey(String surveyName, User user, ProgrammingSkill programmingSkill) {
        this.surveyName = surveyName;
        this.user = user;
        this.programmingSkill = programmingSkill;
    }


}


