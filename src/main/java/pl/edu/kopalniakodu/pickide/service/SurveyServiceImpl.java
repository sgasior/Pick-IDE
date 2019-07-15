package pl.edu.kopalniakodu.pickide.service;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.repository.SurveyRepository;

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
}
