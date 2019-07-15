package pl.edu.kopalniakodu.pickide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.UserService;

import javax.validation.Valid;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processCreateUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "auth/register";
        } else if (userService.findByNickName(user.getNickName()).isPresent()) {
            bindingResult.addError(new ObjectError("name-taken", "User with this name already exists."));
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            model.addAttribute("user", user);
            return "auth/register";
        } else if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.addError(new ObjectError("mail-taken", "User with this email already exists."));
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            model.addAttribute("user", user);
            return "auth/register";
        } else {
            userService.save(user);
            redirectAttributes.addFlashAttribute("successfullyRegistered", true);
            return "redirect:/login";
        }


    }


}
