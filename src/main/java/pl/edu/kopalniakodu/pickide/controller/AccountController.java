package pl.edu.kopalniakodu.pickide.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    UserService userService;
    SurveyService surveyService;

    public AccountController(UserService userService, SurveyService surveyService) {
        this.userService = userService;
        this.surveyService = surveyService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/account")
    public String showAccountProfile(Model model) {

        Optional<User> userOptional = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Survey> allSharedSurveys = surveyService.findAllSharedSurveys(userOptional.get());
        model.addAttribute("surveys", allSharedSurveys);
        return "account/account";
    }

    @PostMapping("/delete")
    public String deleteSurvey(@RequestParam("surveyID") Long surveyID
    ) {
        Survey survey = surveyService.findById(surveyID);

        surveyService.deleteSurvey(survey);
        return "redirect:/account";
    }

}
