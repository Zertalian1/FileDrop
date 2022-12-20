package ru.ccfit.filedrop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String home() {
        System.out.println("Main Page");
        return "redirect:/orders";
    }
}
