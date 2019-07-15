package pl.edu.kopalniakodu.pickide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.service.EnumUtillService;
import pl.edu.kopalniakodu.pickide.service.SurveyService;
import pl.edu.kopalniakodu.pickide.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"share", "preferedCriterias", "notPreferedCriterias", "preferedAlternatives", "notPreferedAlternatives"})
public class SurveyController {

    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);

    private SurveyService surveyService;
    private UserService userService;
    private EnumUtillService enumUtillService;

    public SurveyController(SurveyService surveyService, UserService userService, EnumUtillService enumUtillService) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.enumUtillService = enumUtillService;
    }

    @PostMapping("/survey")
    public String showSurveyForm(
            @RequestParam(value = "share", defaultValue = "false") boolean share,
            @RequestParam(value = "programmerExp") String programmerExp,
            @RequestParam(value = "extraInfo", defaultValue = "false") boolean extra_info,
            @RequestParam(value = "whatCanGain", defaultValue = "false") boolean whatCanGainInfo,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

//        log.info("programmerExp: " + programmerExp);
//        log.info("share: " + share);
//        log.info("extra info: " + extra_info);
//        log.info("whatCanGain " + whatCanGainInfo);

        model.addAttribute("share", share);

        model.addAttribute("preferedCriterias", enumUtillService.preferedCriterias(programmerExp));
        model.addAttribute("notPreferedCriterias", enumUtillService.notPreferedCriterias(programmerExp));

        model.addAttribute("preferedAlternatives", enumUtillService.preferedAlternatives(programmerExp));
        model.addAttribute("notPreferedAlternatives", enumUtillService.notPreferedAlternatives(programmerExp));

        if (extra_info || whatCanGainInfo) {
            return "ahp-info-page";
        }

        if (share) {
            return "redirect:/account/newSurvey";
        } else {
            return "redirect:/newSurvey";
        }

    }

    @GetMapping("/newSurvey")
    public String newSurveyForm(
            Model model,
            @SessionAttribute("preferedCriterias") List<String> preferedCriterias,
            @SessionAttribute("notPreferedCriterias") List<String> notPreferedCriterias,
            @SessionAttribute("preferedAlternatives") List<String> preferedAlternatives,
            @SessionAttribute("notPreferedAlternatives") List<String> notPreferedAlternatives
    ) {

        model.addAttribute("survey", new Survey());

        model.addAttribute("preferedCriterias", preferedCriterias);
        model.addAttribute("notPreferedCriterias", notPreferedCriterias);

        model.addAttribute("preferedAlternatives", preferedAlternatives);
        model.addAttribute("notPreferedAlternatives", notPreferedAlternatives);

        return "survey/survey-form";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/account/newSurvey")
    public String newSurveyFormWithUserLoggedIn(
            @Valid Survey survey,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("survey", new Survey());
        return "survey/survey-form";
    }

    @PostMapping("/processNewSurvey")
    public String processSurveyForm(
            @Valid Survey survey,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("survey", survey);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "newSurvey";
        } else {
            Optional<User> userOptional = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (userOptional.isPresent()) {
                survey.setUser(userOptional.get());
                surveyService.save(survey);
            } else {
                surveyService.save(survey);
            }
            return "redirect:/";
        }

    }


    @PostMapping("/ahp-info-page")
    public String newSurveyFormWithoutAccount(
            @SessionAttribute("share") boolean share
    ) {
        if (share) {
            return "redirect:/account/newSurvey";
        } else {
            return "redirect:/newSurvey";
        }
    }

}


