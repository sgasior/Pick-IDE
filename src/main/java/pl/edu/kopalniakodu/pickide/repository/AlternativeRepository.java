package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.pickide.domain.Alternative;
import pl.edu.kopalniakodu.pickide.domain.Survey;

public interface AlternativeRepository extends JpaRepository<Alternative, Long> {


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Alternative a WHERE a.survey=:survey")
    void deleteAlternativeBySurvey(@Param("survey") Survey survey);
}
