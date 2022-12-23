package ru.ccfit.filedrop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.service.interfaces.UserService;

@Controller
@AllArgsConstructor
public class SecurityController {

    private final UserService userService;

    @GetMapping("/login")
    public String getSignInPage(@ModelAttribute("user") UserDto user){
        return "/pages/LoginPage";
    }

    @PostMapping("/login")
    public String signIn(@ModelAttribute("user") UserDto user){
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto user){
        return "/pages/RegistrationPage";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto user){
        userService.addUser(user);
        return "redirect:/login";
    }
}
