package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.AnswerCriteria;

public interface AnswerCriteriaRepository extends JpaRepository<AnswerCriteria, Long> {
}
