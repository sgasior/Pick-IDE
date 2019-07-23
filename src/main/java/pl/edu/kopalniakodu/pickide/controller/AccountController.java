package pl.edu.kopalniakodu.pickide.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.UserService;

import java.util.Optional;

@Controller
public class AccountController {

    UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/account")
    public String accountProfile() {

        Optional<User> userOptional = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isPresent()) {

        }


        return "account/account";
    }

}
