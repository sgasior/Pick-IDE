package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.AnswerAlternative;

public interface AnswerAlternativeRepository extends JpaRepository<AnswerAlternative, Long> {
}
