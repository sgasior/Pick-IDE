package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Alternative;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.repository.SurveyRepository;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;

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


}
