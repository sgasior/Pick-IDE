package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias);

    void save(Survey survey);

    Map<Criteria, Double> findWeightsOfAllCriteria(List<Comparison<Criteria>> comparisonList, String[] criteriaRating);

}
