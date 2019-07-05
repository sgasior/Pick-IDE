package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
