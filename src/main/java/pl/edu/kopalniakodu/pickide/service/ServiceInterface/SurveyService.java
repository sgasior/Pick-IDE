package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import pl.edu.kopalniakodu.pickide.domain.Survey;

public interface SurveyService {

    Survey save(Survey survey);


    void addCriterias(String[] selectedCriterias, Survey survey);

    void addAlternatives(String[] selectedAlternative, Survey survey);

    boolean isNewCriteriaOrAlternativeAdded(Survey survey);

    String generateRandomUrl();

    Survey findSurveyBySurveyURL(String surveyURL);

    Survey findById(Long surveyID);
}
