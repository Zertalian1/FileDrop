package ru.ccfit.filedrop.service.interfaces;

import ru.ccfit.filedrop.dto.OrderDto;

public interface OrderService {
    OrderDto getOrderById(Long orderId);
}
