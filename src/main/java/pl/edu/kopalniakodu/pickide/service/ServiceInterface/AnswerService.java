package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;

import java.util.List;

public interface AnswerService {
    List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias);
}
