package ru.ccfit.filedrop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainPageController {

    /*Здесь могла быть главная страница*/
    @GetMapping("/")
    public String home() {
        return "redirect:/orders";
    }
}
