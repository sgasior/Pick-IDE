package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.pickide.domain.*;

import java.util.List;

public interface AnswerAlternativeRepository extends JpaRepository<AnswerAlternative, Long> {
    List<AnswerAlternative> findAllByCriteriaAndAlternative(Criteria criteria, Alternative alternative);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM AnswerAlternative answ_alter WHERE answ_alter.answer=:answer")
    void deleteByAnswer(@Param("answer") Answer answer);

}
