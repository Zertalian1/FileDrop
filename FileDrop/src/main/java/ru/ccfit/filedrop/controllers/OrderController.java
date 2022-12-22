package ru.ccfit.filedrop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.entity.File;
import ru.ccfit.filedrop.enumeration.Status;
import ru.ccfit.filedrop.exception.FileException;
import ru.ccfit.filedrop.service.implement.FileServiceImpl;
import ru.ccfit.filedrop.service.implement.OrderServiceImpl;
import ru.ccfit.filedrop.service.implement.UserServiceImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    private final FileServiceImpl fileService;
    private final UserServiceImpl userService;

    @GetMapping("/orders")
    public String getAllOrders(Model model, Principal principal){
        if(principal != null) {
            UserDto user = userService.findByUsername(principal.getName());
            model.addAttribute("orders", orderService.getOrdersByIdUser(user.getId()));
        }
        return "pages/OrdersMainPage";
    }

    @GetMapping("/order/new")
    public String getCreateOrder(){
        return "pages/CreateNewOrder";
    }

    @PostMapping("/order/createOrder")
    public String createOrder(@RequestParam("file") MultipartFile file, Model model, Principal principal) {
        if (file.isEmpty()) {
            return "redirect:/order/new";
        }
        UserDto user = userService.findByUsername(principal.getName());
        OrderDto orderDto = new OrderDto(Status.PROCESS, user);
        orderDto = orderService.addOrder(orderDto);
        FileController.SafeOrderFile(file, user, orderDto, fileService);
        return "redirect:/orders";
    }

    @GetMapping("/blog/{orderId}")
    public String viewOrder(Model model, @PathVariable String orderId){
        OrderDto order= orderService.getOrderById(Long.parseLong(orderId));
        List<File> files = fileService.getFilesByOrderId(order.getId());
        model.addAttribute("order", order);
        model.addAttribute("files", files);
        return "pages/Order";
    }


}
