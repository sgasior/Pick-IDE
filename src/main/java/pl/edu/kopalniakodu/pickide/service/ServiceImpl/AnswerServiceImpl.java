package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private SurveyService surveyService;

    public AnswerServiceImpl(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias) {

        List<Comparison<Criteria>> result = new ArrayList<>();

        for (int i = 0; i < criterias.size() - 1; i++) {
            int nextIndex = i + 1;
            while (nextIndex < criterias.size()) {
                Comparison<Criteria> criteriaComparison = new Comparison<>(criterias.get(i), criterias.get(nextIndex));
                result.add(criteriaComparison);
                nextIndex = nextIndex + 1;
            }
        }
        return result;
    }


    @Override
    public Map<Criteria, Double> findWeightsOfAllCriteria(List<Comparison<Criteria>> comparisonList, String[] criteriaRating) {
        for (int i = 0; i < criteriaRating.length; i++) {
            comparisonList.get(i).setValue(Integer.parseInt(criteriaRating[i]));
        }

        for (Comparison<Criteria> comparison : comparisonList) {
            System.out.println(comparison.getChoice1() + " - " + comparison.getChoice2() + " value: " + comparison.getValue());
        }

        return null;
    }


    @Override
    public void save(Survey survey) {
        log.info(("method save() not impl"));
    }


}