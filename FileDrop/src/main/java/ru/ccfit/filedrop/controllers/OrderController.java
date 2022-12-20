package ru.ccfit.filedrop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.service.implement.OrderServiceImpl;
import ru.ccfit.filedrop.service.implement.UserServiceImpl;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class OrderController {
    OrderServiceImpl orderService;
    UserServiceImpl userService;

    @GetMapping("/orders")

    public String getAllOrders(Model model, Principal principal){
        System.out.println("addadda");
        UserDto user = userService.findByUsername(principal.getName());
        model.addAttribute("orders", orderService.getOrdersByIdUser(user.getId()));
        return "pages/OrdersMainPage";
    }
}
