package ru.ccfit.filedrop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    /*Здесь могла быть главная страница*/
    @GetMapping("/")
    public String home() {
        return "redirect:/orders";
    }
}
