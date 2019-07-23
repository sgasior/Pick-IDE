package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Criteria c WHERE c.survey=:survey")
    void deleteCriteriaBySurvey(@Param("survey") Survey survey);

}
