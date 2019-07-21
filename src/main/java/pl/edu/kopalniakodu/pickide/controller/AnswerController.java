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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

        Map<Criteria, Double> weightsOfAllCriteria = answerService
                .findWeightsOfAllCriteria(criteriaComparisonList, criteriaRating, survey.getCriterias());

        Answer answer = new Answer(survey);
        answerService.save(answer);
        answerService.saveAnswerCriteria(answer, weightsOfAllCriteria);

        model.addAttribute("answer_id", answer.getId());

        if (survey.isAutomaticAlternativeRating()) {
            //TODO
            //redirect
            return "result-page";
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


        Map<Alternative, Double> weightsOfAllAlternative = answerService
                .findWeightsOfAllAlternative(alternativeComparisonList, alternativeRating, survey.getAlternatives());


        answerService.save(answer);
        answerService.saveAnswerAlternative(answer, weightsOfAllAlternative, criteria);

        if (criteriaQueue.isEmpty()) {
            if (survey.getSurveyURIParam() != null) {
                return "survey/acknowledgement";
            } else {
                return "result-page";
            }
        } else {
            model.addAttribute("criteriaName", ((LinkedList<Criteria>) criteriaQueue).get(0).getCriteriaName());
            return "survey/answerAlternative";
        }
    }
}
