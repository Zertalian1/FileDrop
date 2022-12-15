package ru.ccfit.filedrop.service.interfaces;

import ru.ccfit.filedrop.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long orderId);
    void deleteOrder(OrderDto orderDto);
    OrderDto addOrder(OrderDto orderDto);
    void updateOrder(OrderDto orderDto);
    List<OrderDto> getOrdersByIdUser(Long userId);

}
