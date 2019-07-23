package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.pickide.domain.Answer;
import pl.edu.kopalniakodu.pickide.domain.Survey;

public interface AnswerRepository extends JpaRepository<Answer, Long> {


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Answer a WHERE a.survey=:survey")
    void deleteAnswerBySurvey(@Param("survey") Survey survey);

}
