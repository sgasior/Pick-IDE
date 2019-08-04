package pl.edu.kopalniakodu.pickide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomePageController {


    @GetMapping({"/", "/index", "index.html"})
    public String welcomePage() {
        return "index";
    }

    @GetMapping("/start")
    public String startPage() {
        return "survey/survey-start";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }
}
