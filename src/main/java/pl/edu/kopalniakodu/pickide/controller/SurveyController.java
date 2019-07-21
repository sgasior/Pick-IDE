package pl.edu.kopalniakodu.pickide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.EnumUtillService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"share", "preferedCriterias",
        "notPreferedCriterias", "preferedAlternatives",
        "notPreferedAlternatives", "programmerExp",
        "survey", "surveyURL"})
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

        model.addAttribute("share", share);
        model.addAttribute("programmerExp", programmerExp);

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

        addAtributesToNewSurveyForm(model, preferedCriterias, notPreferedCriterias, preferedAlternatives, notPreferedAlternatives);

        return "survey/survey-form";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/account/newSurvey")
    public String newSurveyFormWithUserLoggedIn(
            Model model,
            @SessionAttribute("preferedCriterias") List<String> preferedCriterias,
            @SessionAttribute("notPreferedCriterias") List<String> notPreferedCriterias,
            @SessionAttribute("preferedAlternatives") List<String> preferedAlternatives,
            @SessionAttribute("notPreferedAlternatives") List<String> notPreferedAlternatives
    ) {
        addAtributesToNewSurveyForm(model, preferedCriterias, notPreferedCriterias, preferedAlternatives, notPreferedAlternatives);
        return "survey/survey-form";
    }

    @PostMapping("/processNewSurvey")
    public String processSurveyForm(
            @Valid Survey survey,
            BindingResult bindingResult,
            Model model,
            @RequestParam(value = "selectedCriteria", required = false) String[] selectedCriterias,
            @RequestParam(value = "selectedAlternative", required = false) String[] selectedAlternative,
            @SessionAttribute("programmerExp") String programmerExp
    ) {

        validateSurveyForm(bindingResult, selectedCriterias, selectedAlternative);


        if (bindingResult.hasErrors()) {
            model.addAttribute("survey", survey);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            model.addAttribute("selectedCriterias", selectedCriterias);
            model.addAttribute("selectedAlternatives", selectedAlternative);
            return "survey/survey-form";
        } else {

            surveyService.addCriterias(selectedCriterias, survey);
            surveyService.addAlternatives(selectedAlternative, survey);

            model.addAttribute("survey", survey);
            model.addAttribute("skillMidOrPro", enumUtillService.isProgrammingSkillMidOrPro(programmerExp));


            if (surveyService.isNewCriteriaOrAlternativeAdded(survey)) {
                model.addAttribute("isAutomaticRatingEnable", false);
            } else {
                model.addAttribute("isAutomaticRatingEnable", true);
            }

            return "survey/survey-form-extra-info";
        }

    }

    @PostMapping("/generateSurvey")
    public String generateSurvey(
            @SessionAttribute("survey") Survey survey,
            @SessionAttribute("share") boolean share,
            @RequestParam(value = "rating", required = false) String rating,
            Model model,
            HttpServletRequest request
    ) {
        if (rating == null) {
            survey.setAutomaticAlternativeRating(false);
        } else if (rating.equals("automatic-rating")) {
            survey.setAutomaticAlternativeRating(true);
        }

        Optional<User> userOptional = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isPresent()) {
            survey.setUser(userOptional.get());
        }

        if (share) {
            String surveyURIParam;
            do {
                surveyURIParam = surveyService.generateRandomURIParam();
            } while (surveyService.findSurveyBySurveyURIParam(surveyURIParam).isPresent());

            survey.setSurveyURIParam(surveyURIParam);

            String surveyURL = surveyService.generateSurveyURL("answer/" + surveyURIParam, request);

            surveyService.save(survey);
            model.addAttribute("surveyURL", surveyURL);
            return "redirect:/survey/share";

        } else {
            surveyService.save(survey);
            return "redirect:/answer";
        }


    }

    @GetMapping("/survey/share")
    public String surveyCreatedInfo(
            @SessionAttribute("surveyURL") String surveyURL,
            Model model
    ) {
        model.addAttribute("surveyURL", surveyURL);
        return "survey/share-link";
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


    private void validateSurveyForm(BindingResult bindingResult, @RequestParam(value = "selectedCriteria", required = false) String[] selectedCriterias, @RequestParam(value = "selectedAlternative", required = false) String[] selectedAlternative) {

        if (selectedCriterias == null && selectedCriterias == null) {
            bindingResult.addError(new ObjectError(
                    "null-criteria-and-alternative",
                    "You have to choose at least 2 criteria and 2 alternative")
            );
            return;
        }


        if (selectedCriterias == null || selectedCriterias.length <= 1) {
            bindingResult.addError(new ObjectError(
                    "min-criteria-error",
                    "You have to choose at least 2 criteria")
            );
            return;
        }


        if (selectedAlternative == null || selectedAlternative.length <= 1) {
            bindingResult.addError(new ObjectError(
                    "min-criteria-error",
                    "You have to choose at least 2 alternative")
            );
            return;
        }

        if (selectedCriterias.length > 4) {
            bindingResult.addError(new ObjectError(
                    "max-criteria-error",
                    "You have chosen more criteria than it should be. You can select max 4.")
            );
        }

        if (selectedAlternative.length > 4) {
            bindingResult.addError(new ObjectError(
                    "max-alternative-error\"",
                    "You have chosen more alternative than it should be. You can select max 4.")
            );
        }

        if (hasDuplicate(selectedCriterias)) {
            bindingResult.addError(new ObjectError(
                    "duplicate-criteria",
                    "You cannot choose two same criteria")
            );
        }

        if (hasDuplicate(selectedAlternative)) {
            bindingResult.addError(new ObjectError(
                    "duplicate-criteria",
                    "You cannot choose two same alternative")
            );
        }

    }

    private static boolean hasDuplicate(String[] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                if (tab[i].equals(tab[j]) && i != j) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addAtributesToNewSurveyForm(Model model, @SessionAttribute("preferedCriterias") List<String> preferedCriterias, @SessionAttribute("notPreferedCriterias") List<String> notPreferedCriterias, @SessionAttribute("preferedAlternatives") List<String> preferedAlternatives, @SessionAttribute("notPreferedAlternatives") List<String> notPreferedAlternatives) {

        model.addAttribute("survey", new Survey());

        model.addAttribute("preferedCriterias", preferedCriterias);
        model.addAttribute("notPreferedCriterias", notPreferedCriterias);

        model.addAttribute("preferedAlternatives", preferedAlternatives);
        model.addAttribute("notPreferedAlternatives", notPreferedAlternatives);
    }


}


