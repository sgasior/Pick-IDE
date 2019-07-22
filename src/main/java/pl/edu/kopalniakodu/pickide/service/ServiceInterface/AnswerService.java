package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import pl.edu.kopalniakodu.pickide.domain.Alternative;
import pl.edu.kopalniakodu.pickide.domain.Answer;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnswerService {
    List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias);

    Map<Criteria, Map<Double, Double>> findWeightsOfAllCriteria(List<Comparison<Criteria>> comparisonList, String[] criteriaRating, List<Criteria> criteriaList);

    void saveAnswerCriteria(Answer answer, Map<Criteria, Map<Double, Double>> weightsOfAllCriteria);

    void save(Answer answer);

    List<Comparison<Alternative>> findAllAlternativeComparison(List<Alternative> alternatives);

    Optional<Answer> findAnswerById(Long answer_id);

    Map<Alternative, Map<Double, Double>> findWeightsOfAllAlternative(List<Comparison<Alternative>> alternativeComparisonList, String[] alternativeRating, List<Alternative> alternatives);

    void saveAnswerAlternative(Answer answer, Map<Alternative, Map<Double, Double>> weightsOfAllAlternative, Criteria criteria);
}
