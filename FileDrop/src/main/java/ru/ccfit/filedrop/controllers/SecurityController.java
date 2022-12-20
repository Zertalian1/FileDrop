package ru.ccfit.filedrop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.service.implement.UserServiceImpl;

@Controller
@AllArgsConstructor
public class SecurityController {

    private final UserServiceImpl userService;

    @GetMapping("/login")
    public String getSign_inPage(@ModelAttribute("user") UserDto user){
        return "/pages/LoginPage";
    }

    @PostMapping("/login")
    public String sign_in(@ModelAttribute("user") UserDto user){
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
