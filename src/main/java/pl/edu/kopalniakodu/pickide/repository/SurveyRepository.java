package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.User;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findBySurveyName(String surveyName);

    Optional<Survey> findSurveyBySurveyURIParam(String surveyURIParam);

    List<Survey> findAllByUserAndSurveyURIParamNotNull(User user);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Survey s WHERE s.id=:id")
    void deleteById(@Param("id") Long id);

}
