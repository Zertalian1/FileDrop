package ru.ccfit.filedrop.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.entity.Order;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.mapper.OrderMapper;
import ru.ccfit.filedrop.repository.OrderRepository;
import ru.ccfit.filedrop.service.interfaces.OrderService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDto getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(orderMapper::orderToOrderDto).orElseThrow(
                () -> new NotFoundException("Заказ с id: " + orderId + " не найден!")
        );
    }
}
