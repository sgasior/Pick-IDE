package pl.edu.kopalniakodu.pickide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.kopalniakodu.pickide.domain.Alternative;
import pl.edu.kopalniakodu.pickide.domain.Answer;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;

import java.util.*;

@Controller
@SessionAttributes({"survey", "comparisons", "answer_id", "criteriaQueue"})
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private AnswerService answerService;
    private SurveyService surveyService;

    public AnswerController(AnswerService answerService, SurveyService surveyService) {
        this.answerService = answerService;
        this.surveyService = surveyService;
    }

    @GetMapping("/answer/{surveyURIParam}")
    public String newAnswerForm(
            @PathVariable(value = "surveyURIParam") String surveyURIParam,
            Model model
    ) {
        Survey survey = surveyService.findSurveyBySurveyURIParam(surveyURIParam).get();
        List<Comparison<Criteria>> comparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("survey", survey);
        model.addAttribute("comparisons", comparisonList);
        return "survey/answerCriteria";
    }


    @GetMapping("/answer")
    public String newAnswerForm(
            @SessionAttribute("survey") Survey survey,
            Model model
    ) {
        List<Comparison<Criteria>> criteriaComparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("comparisons", criteriaComparisonList);
        return "survey/answerCriteria";
    }


    @PostMapping("/answer")
    public String processNewAnswerCriteria(
            @SessionAttribute("survey") Survey survey,
            @RequestParam(value = "criteriaRating") String[] criteriaRating,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        List<Comparison<Criteria>> criteriaComparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        Map<Criteria, Map<Double, Double>> weightsOfAllCriteria = answerService
                .findWeightsOfAllCriteria(criteriaComparisonList, criteriaRating, survey.getCriterias());

        Answer answer = new Answer(survey);
        answerService.save(answer);
        answerService.saveAnswerCriteria(answer, weightsOfAllCriteria);

        model.addAttribute("answer_id", answer.getId());

        if (survey.isAutomaticAlternativeRating() && survey.getSurveyURIParam() != null) {
            return "survey/acknowledgement";
        } else if (survey.isAutomaticAlternativeRating() && survey.getSurveyURIParam() == null) {

            prepareResultModel(model, survey);
            return "survey/result-page";
        } else {

            //share and not share option
            List<Comparison<Alternative>> alternativeComparisonList = answerService.findAllAlternativeComparison(survey.getAlternatives());
            Queue<Criteria> criteriaQueue = new LinkedList<>(survey.getCriterias());
            model.addAttribute("criteriaQueue", criteriaQueue);
            model.addAttribute("comparisons", alternativeComparisonList);
            model.addAttribute("criteriaName", ((LinkedList<Criteria>) criteriaQueue).get(0).getCriteriaName());
            return "survey/answerAlternative";
        }

    }


    @PostMapping("/answer/alternative")
    public String processNewAnswerAlternative(
            @RequestParam(value = "alternativeRating") String[] alternativeRating,
            @SessionAttribute("survey") Survey survey,
            @SessionAttribute("answer_id") Long answer_id,
            @SessionAttribute("criteriaQueue") Queue<Criteria> criteriaQueue,
            Model model
    ) {

        Long criteriaID = criteriaQueue.remove().getId();

        Answer answer = answerService.findAnswerById(answer_id).get();
        Criteria criteria = surveyService.findCriteriaById(criteriaID).get();
        List<Comparison<Alternative>> alternativeComparisonList = answerService.findAllAlternativeComparison(survey.getAlternatives());


        Map<Alternative, Map<Double, Double>> weightsOfAllAlternative = answerService
                .findWeightsOfAllAlternative(alternativeComparisonList, alternativeRating, survey.getAlternatives());


        answerService.save(answer);
        answerService.saveAnswerAlternative(answer, weightsOfAllAlternative, criteria);

        if (criteriaQueue.isEmpty()) {
            if (survey.getSurveyURIParam() != null) {
                return "survey/acknowledgement";
            } else {

                prepareResultModel(model, survey);
                return "survey/result-page";


            }
        } else {
            model.addAttribute("criteriaName", ((LinkedList<Criteria>) criteriaQueue).get(0).getCriteriaName());
            return "survey/answerAlternative";
        }
    }


    @GetMapping("/result/{surveyURIParam}")
    public String result(
            @PathVariable(value = "surveyURIParam") String surveyURIParam,
            Model model
    ) {
        Survey survey = surveyService.findSurveyBySurveyURIParam(surveyURIParam).get();

        boolean isFilledAtLeastOnce = false;
        for (Answer answer : survey.getAnswers()) {
            if (answer.getAnswerAlternative().size() > 0 && answer.getAnswerCriteria().size() > 0) {
                isFilledAtLeastOnce = true;
            }
        }
        if (!isFilledAtLeastOnce) {
            return "survey/no-answers";
        }

        prepareResultModel(model, survey);
        return "survey/result-page";
    }

    private void prepareResultModel(Model model, Survey survey) {
        Map<Criteria, Double> averageWeightsOfAllCriteria = answerService.findAverageWeightsOfAllCriteria(survey);
        Map<Alternative, Double> ranking = answerService.rank(survey);

        Map<Alternative, Double> firstPlaceMap = getTopPlace(ranking, 0);
        Map<Alternative, Double> secondPlaceMap = getTopPlace(ranking, firstPlaceMap.size());
        Map<Alternative, Double> thirdPlaceMap = getTopPlace(ranking, secondPlaceMap.size() + firstPlaceMap.size());
        Map<Alternative, Double> lastPlaceMap = getTopPlace(ranking, secondPlaceMap.size() + firstPlaceMap.size() + thirdPlaceMap.size());

        model.addAttribute("firstPlaceMap", firstPlaceMap);
        model.addAttribute("secondPlaceMap", secondPlaceMap);
        model.addAttribute("thirdPlaceMap", thirdPlaceMap);
        model.addAttribute("lastPlaceMap", lastPlaceMap);

        model.addAttribute("weights", averageWeightsOfAllCriteria);
    }

    private Map<Alternative, Double> getTopPlace(Map<Alternative, Double> ranking, int placeNumber) {
        Map<Alternative, Double> map = new HashMap();
        ArrayList<Double> valueList = new ArrayList<Double>(ranking.values());
        Collections.sort(valueList, Collections.reverseOrder());

        if (valueList.size() <= placeNumber) {
            return map;
        }
        for (Alternative alternative : getKeysFromValue(ranking, valueList.get(placeNumber))) {
            map.put(alternative, valueList.get(placeNumber));
        }
        return map;
    }


    public List<Alternative> getKeysFromValue(Map<Alternative, Double> hm, Double value) {
        List<Alternative> list = new ArrayList<Alternative>();
        for (Alternative o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                list.add(o);
            }
        }
        return list;
    }

}
