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

    @GetMapping("answer/criteria/{surveyURIParam}")
    public String newAnswerForm(
            @PathVariable(value = "surveyURIParam") String surveyURIParam,
            Model model
    ) {
        Survey survey = surveyService.findSurveyBySurveyURIParam(surveyURIParam).get();
        List<Comparison<Criteria>> comparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("surveyID", survey.getId());
        model.addAttribute("comparisons", comparisonList);
        return "survey/answerCriteria";
    }


    @GetMapping("/answer/criteria")
    public String newAnswerForm(
            @SessionAttribute("survey") Survey survey,
            Model model
    ) {
        List<Comparison<Criteria>> criteriaComparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("surveyID", survey.getId());
        model.addAttribute("comparisons", criteriaComparisonList);
        return "survey/answerCriteria";
    }


    @PostMapping("/answer/criteria")
    public String processNewAnswerCriteria(
            @RequestParam("surveyID") Long surveyID,
            @RequestParam(value = "criteriaRating") String[] criteriaRating,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Survey survey = surveyService.findById(surveyID);
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

            if (survey.getSurveyURIParam() != null) {
                redirectAttributes.addAttribute("surveyURIParam", survey.getSurveyURIParam());
                return "redirect:/answer/alternative/{surveyURIParam}";
            } else {
                return "redirect:/answer/alternative";
            }

        }

    }


    @GetMapping("answer/alternative/{surveyURIParam}")
    public String newAlternativeAnswerForm(
            @PathVariable(value = "surveyURIParam") String surveyURIParam,
            @SessionAttribute(value = "answer_id", required = false) Long answer_id,
            Model model
    ) {

        Survey survey = surveyService.findSurveyBySurveyURIParam(surveyURIParam).get();
        log.info("answer_id " + answer_id);
        if (answer_id == null) {
            Answer answer = new Answer(survey);
            answerService.save(answer);
            model.addAttribute("answer_id", answer.getId());
        }

        List<Comparison<Alternative>> alternativeComparisonList = answerService.findAllAlternativeComparison(survey.getAlternatives());

        Queue<Criteria> criteriaQueue = new LinkedList<>(survey.getCriterias());
        model.addAttribute("criteriaQueue", criteriaQueue);
        model.addAttribute("surveyID", survey.getId());
        model.addAttribute("comparisons", alternativeComparisonList);
        return "survey/answerAlternative";
    }


    @GetMapping("/answer/alternative")
    public String newAlternativeAnswerForm(
            @SessionAttribute("survey") Survey survey,
            Model model
    ) {

        List<Comparison<Alternative>> alternativeComparisonList = answerService.findAllAlternativeComparison(survey.getAlternatives());

        Queue<Criteria> criteriaQueue = new LinkedList<>(survey.getCriterias());
        model.addAttribute("criteriaQueue", criteriaQueue);
        model.addAttribute("surveyID", survey.getId());
        model.addAttribute("comparisons", alternativeComparisonList);
        return "survey/answerAlternative";
    }


    @PostMapping("/answer/alternative")
    public String processNewAnswerAlternative(
            @RequestParam("surveyID") Long surveyID,
            @RequestParam(value = "alternativeRating") String[] alternativeRating,
            @SessionAttribute("answer_id") Long answer_id,
            @SessionAttribute("criteriaQueue") Queue<Criteria> criteriaQueue,
            Model model
    ) {

        Long criteriaID = criteriaQueue.remove().getId();

        Survey survey = surveyService.findById(surveyID);
        Answer answer = answerService.findAnswerById(answer_id).get();
        Criteria criteria = surveyService.findCriteriaById(criteriaID).get();
        List<Comparison<Alternative>> alternativeComparisonList = answerService.findAllAlternativeComparison(survey.getAlternatives());


        log.info("Criteria: " + surveyService.findCriteriaById(criteriaID).get().getCriteriaName());
        log.info("Answer id: " + answer.getId());

        Map<Alternative, Double> weightsOfAllAlternative = answerService
                .findWeightsOfAllAlternative(alternativeComparisonList, alternativeRating, survey.getAlternatives());


        answerService.save(answer);
        answerService.saveAnswerAlternative(answer, weightsOfAllAlternative, criteria);

        if (criteriaQueue.isEmpty()) {
            return "result-page";
        } else {
            model.addAttribute("surveyID", survey.getId());
            return "survey/answerAlternative";
        }


    }


}
