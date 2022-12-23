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
import ru.ccfit.filedrop.enumeration.Role;
import ru.ccfit.filedrop.enumeration.Status;
import ru.ccfit.filedrop.service.interfaces.FileService;
import ru.ccfit.filedrop.service.interfaces.OrderService;
import ru.ccfit.filedrop.service.interfaces.UserService;

import java.io.IOException;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private final FileService fileService;
    private final UserService userService;

    @GetMapping("/orders")
    public String getAllOrders(Model model, Principal principal){
        if(principal != null) {
            UserDto user = userService.findByUsername(principal.getName());
            switch (user.getRole()) {
                case WORKER,ADMIN ->
                    model.addAttribute("orders", orderService.getAllOrders().stream().filter(o->o.getStatus()!=Status.DELETED).toList());
                default ->
                        model.addAttribute("orders", orderService.getOrdersByIdUser(user.getId()).stream().filter(o->o.getStatus()!=Status.DELETED).toList());
            }
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
        OrderDto orderDto = new OrderDto(Status.PROCESS, user, OffsetDateTime.now());
        orderDto = orderService.addOrder(orderDto);
        FileDto fileDto = new FileDto(file.getOriginalFilename(), user, orderDto, OffsetDateTime.now());
        fileService.saveFile(fileDto, file);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrder(Model model, @PathVariable String orderId){
        OrderDto order= orderService.getOrderById(Long.parseLong(orderId));
        List<FileDto> files = fileService.getFilesByOrderId(order.getId());
        model.addAttribute("order", order);
        model.addAttribute("files", files);
        return "pages/Order";
    }

    @PostMapping("/order/{orderId}/delete")
    public String deleteOrder(@PathVariable String orderId){
        orderService.changeOrderStatus(Long.parseLong(orderId), Status.DELETED);
        return "redirect:/orders";
    }

    @PostMapping("/order/{orderId}/changeStatus")
    public String changeOrderStatus(@PathVariable String orderId){
        orderService.changeOrderStatus(Long.parseLong(orderId), Status.COMPLETED);
        return "redirect:/orders";
    }

}
