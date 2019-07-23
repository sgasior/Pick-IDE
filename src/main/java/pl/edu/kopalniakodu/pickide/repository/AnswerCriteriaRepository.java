package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.pickide.domain.Answer;
import pl.edu.kopalniakodu.pickide.domain.AnswerCriteria;
import pl.edu.kopalniakodu.pickide.domain.Criteria;

import java.util.List;

public interface AnswerCriteriaRepository extends JpaRepository<AnswerCriteria, Long> {

//    @Query("SELECT a FROM AnswerCriteria a WHERE a.answer=:answer")
//    List<AnswerCriteria> findAllByAnswerCriteriaByAnswer(@Param("answer") Answer answer);

    List<AnswerCriteria> findAllByCriteria(Criteria criteria);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM AnswerCriteria answ_crit WHERE answ_crit.answer=:answer")
    void deleteByAnswer(@Param("answer") Answer answer);
}
