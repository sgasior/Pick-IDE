package pl.edu.kopalniakodu.pickide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SurveyController {

    @GetMapping("/survey")
    public String newSurveyForm() {
        return "survey/survey-form";
    }

}
