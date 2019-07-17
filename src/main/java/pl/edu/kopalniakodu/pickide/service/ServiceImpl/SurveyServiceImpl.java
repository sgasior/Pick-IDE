package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Alternative;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.PreferedCriteria;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.PreferedIDE;
import pl.edu.kopalniakodu.pickide.repository.SurveyRepository;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    private SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey save(Survey survey) {
        surveyRepository.save(survey);
        return survey;
    }

    @Override
    public void addCriterias(String[] selectedCriterias, Survey survey) {
        for (String criteriaName : selectedCriterias) {
            survey.addCriteria(new Criteria(criteriaName));
        }
    }

    @Override
    public void addAlternatives(String[] selectedAlternative, Survey survey) {
        for (String alternativeName : selectedAlternative) {
            survey.addAlternative(new Alternative(alternativeName));
        }
    }

    @Override
    public boolean isNewCriteriaOrAlternativeAdded(Survey survey) {

        List<String> allPredefiniedCriteriaAndAlternative = new ArrayList<>();
        boolean result = false;

        allPredefiniedCriteriaAndAlternative.addAll(PreferedCriteria.BEGINNER.getReponse());
        allPredefiniedCriteriaAndAlternative.addAll(PreferedCriteria.MID_EXP.getReponse());
        allPredefiniedCriteriaAndAlternative.addAll(PreferedCriteria.PRO_EXP.getReponse());

        allPredefiniedCriteriaAndAlternative.addAll(PreferedIDE.BEGINNER.getReponse());
        allPredefiniedCriteriaAndAlternative.addAll(PreferedIDE.MID_EXP.getReponse());
        allPredefiniedCriteriaAndAlternative.addAll(PreferedIDE.PRO_EXP.getReponse());

        List<Criteria> criteriaList = survey.getCriterias();
        for (Criteria criteria : criteriaList) {
            if (allPredefiniedCriteriaAndAlternative.contains(criteria.getCriteriaName())) {

            } else {
                return true;
            }
        }

        List<Alternative> alternativeList = survey.getAlternatives();
        for (Alternative alternative : alternativeList) {
            if (allPredefiniedCriteriaAndAlternative.contains(alternative.getAlternativeName())) {

            } else {
                return true;
            }
        }
        return result;
    }


}
