package ru.ccfit.filedrop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.enumeration.Status;
import ru.ccfit.filedrop.service.implement.FileServiceImpl;
import ru.ccfit.filedrop.service.implement.OrderServiceImpl;
import ru.ccfit.filedrop.service.implement.UserServiceImpl;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class OrderController {
    OrderServiceImpl orderService;

    FileServiceImpl fileService;
    UserServiceImpl userService;

    @GetMapping("/orders")
    public String getAllOrders(Model model, Principal principal){
        if(principal != null) {
            UserDto user = userService.findByUsername(principal.getName());
            model.addAttribute("orders", orderService.getOrdersByIdUser(user.getId()));
        }
        return "pages/OrdersMainPage";
    }

    @GetMapping("/order/new")
    public String createOrder(){
        return "pages/CreateNewOrder";
    }

    @PostMapping("/order/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, Principal principal) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "redirect:/order/new";
        }
        UserDto user = userService.findByUsername(principal.getName());
        OrderDto orderDto = new OrderDto();
        orderDto.setCreateDateTime(OffsetDateTime.now());
        orderDto.setStatus(Status.PROCESS);
        orderDto.setUser(user);
        orderDto = orderService.addOrder(orderDto);


        FileDto fileDto = new FileDto();
        fileDto.setName(file.getOriginalFilename());
        fileDto.setCreateDateTime(OffsetDateTime.now());
        fileDto.setOrder(orderDto);
        fileDto.setOwnerUser(user);
        fileService.saveFile(fileDto, file);

        // return success response
        model.addAttribute("message", "You successfully uploaded file!");

        return "redirect:/orders";
    }
}
