package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface SurveyService {

    Survey save(Survey survey);


    void addCriterias(String[] selectedCriterias, Survey survey);

    void addAlternatives(String[] selectedAlternative, Survey survey);

    boolean isNewCriteriaOrAlternativeAdded(Survey survey);

    String generateRandomURIParam();

    Optional<Survey> findSurveyBySurveyURIParam(String surveyURL);

    Survey findById(Long surveyID);

    String generateSurveyURL(String s, HttpServletRequest request);

    Optional<Criteria> findCriteriaById(Long criteriaID);
}
