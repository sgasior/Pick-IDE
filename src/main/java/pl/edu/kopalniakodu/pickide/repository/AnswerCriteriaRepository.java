package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.AnswerCriteria;
import pl.edu.kopalniakodu.pickide.domain.Criteria;

import java.util.List;

public interface AnswerCriteriaRepository extends JpaRepository<AnswerCriteria, Long> {

//    @Query("SELECT a FROM AnswerCriteria a WHERE a.answer=:answer")
//    List<AnswerCriteria> findAllByAnswerCriteriaByAnswer(@Param("answer") Answer answer);

    List<AnswerCriteria> findAllByCriteria(Criteria criteria);
}
