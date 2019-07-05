package pl.edu.kopalniakodu.pickide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.pickide.domain.Criteria;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {


}
