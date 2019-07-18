package pl.edu.kopalniakodu.pickide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.edu.kopalniakodu.pickide.domain.Answer;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;

import java.util.List;

@Controller
@SessionAttributes("survey")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/answer")
    public String test(
            @SessionAttribute("survey") Survey survey,
            Model model
    ) {
        log.info("Survey id: " + survey.getId());

        List<Comparison<Criteria>> comparisonList = answerService.findAllCriteriaComparison(survey.getCriterias());

        model.addAttribute("comparisons", comparisonList);
        model.addAttribute("answer", new Answer(survey));
        return "survey/answer";
    }

}
