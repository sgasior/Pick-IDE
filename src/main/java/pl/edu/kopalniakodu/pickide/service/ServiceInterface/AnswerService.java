package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import pl.edu.kopalniakodu.pickide.domain.Answer;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias);

    Map<Criteria, Double> findWeightsOfAllCriteria(List<Comparison<Criteria>> comparisonList, String[] criteriaRating, List<Criteria> criteriaList);

    void saveAnswerCriteria(Answer answer, Map<Criteria, Double> weightsOfAllCriteria);

    void save(Answer answer);
}
