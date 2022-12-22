package ru.ccfit.filedrop.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.entity.Order;
import ru.ccfit.filedrop.exception.IntegrationException;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.mapper.OrderMapper;
import ru.ccfit.filedrop.repository.OrderRepository;
import ru.ccfit.filedrop.service.interfaces.FileService;
import ru.ccfit.filedrop.service.interfaces.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final FileService fileService;
    private final OrderRepository orderRepository;

    @Override
    public OrderDto getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(orderMapper::orderToOrderDto).orElseThrow(
                () -> new NotFoundException("Заказ с id: " + orderId + " не найден!")
        );
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Заказ с id: " + orderId + " не найден!")
        );

        fileService.getFilesByOrderId(orderId).forEach(fileService::deleteFile);

        orderRepository.delete(order);
    }

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        Order orderToSave = orderMapper.orderDtoToOrder(orderDto);
        try {
            return orderMapper.orderToOrderDto(orderRepository.save(orderToSave));
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrationException("Попытка добавления заказа приводит к нарушению целостности данных");
        }
    }

    @Override
    public void updateOrder(Long orderId, OrderDto orderDto) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);

        foundOrder.ifPresentOrElse(
                order -> orderRepository.save(
                        OrderMapper.updateOrderByNotNullFieldsOfOrderDto(order, orderDto)),
                () -> {
                    throw new NotFoundException("Клиент с id: " + orderDto.getId() + " не найден!");
                }
        );
    }

    @Override
    public List<OrderDto> getOrdersByIdUser(Long userId) {
        return listOrderToListOrderDto(orderRepository.getOrderByUserId(userId));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return listOrderToListOrderDto(orders);
    }

    /**
     * Маппинг списка заказов в список дто
     *
     * @param orders список Order
     * @return список дто order
     */
    private List<OrderDto> listOrderToListOrderDto(List<Order> orders) {
        return orders.stream().map(orderMapper::orderToOrderDto).collect(Collectors.toList());
    }
}
