package pl.edu.kopalniakodu.pickide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"survey", "comparisons"})
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private AnswerService answerService;
    private SurveyService surveyService;

    public AnswerController(AnswerService answerService, SurveyService surveyService) {
        this.answerService = answerService;
        this.surveyService = surveyService;
    }

    @GetMapping("answer/{surveyURL}")
    public String newAnswerForm(
            @PathVariable(value = "surveyURL") String surveyURL,
            Model model
    ) {
        Survey survey = surveyService.findSurveyBySurveyURL(surveyURL);
        List<Comparison<Criteria>> comparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("surveyID", survey.getId());
        model.addAttribute("comparisons", comparisonList);
        return "survey/answer";
    }


    @GetMapping("/answer")
    public String newAnswerForm(
            @SessionAttribute("survey") Survey survey,
            Model model
    ) {
        List<Comparison<Criteria>> comparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("surveyID", survey.getId());
        model.addAttribute("comparisons", comparisonList);
        return "survey/answer";
    }


    @PostMapping("/processNewAnswerCriteria")
    public String processNewAnswerCriteria(
            @RequestParam("surveyID") Long surveyID,
            @RequestParam(value = "criteriaRating") String[] criteriaRating
    ) {
        log.info("Survey id: " + surveyID);
        Survey survey = surveyService.findById(surveyID);
        List<Comparison<Criteria>> comparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());
        log.info("" + survey.isAutomaticAlternativeRating());

        Map<Criteria, Double> answerCriteria = answerService
                .findWeightsOfAllCriteria(comparisonList, criteriaRating);

        answerService.save(survey);


        return "index";
    }

}
