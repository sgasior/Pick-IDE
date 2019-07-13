package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
