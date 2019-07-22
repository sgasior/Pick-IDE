package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.Alternative;
import pl.edu.kopalniakodu.pickide.domain.AnswerAlternative;
import pl.edu.kopalniakodu.pickide.domain.Criteria;

import java.util.List;

public interface AnswerAlternativeRepository extends JpaRepository<AnswerAlternative, Long> {
    List<AnswerAlternative> findAllByCriteriaAndAlternative(Criteria criteria, Alternative alternative);
}
