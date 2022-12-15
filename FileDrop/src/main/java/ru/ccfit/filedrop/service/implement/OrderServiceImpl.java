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
import ru.ccfit.filedrop.service.interfaces.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    /**
     * Нахождение нужного заказа по его ID
     *
     * @param orderId заказ, который необходимо найти
     * @return OrderDto найденный заказ
     */
    @Override
    public OrderDto getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(orderMapper::orderToOrderDto).orElseThrow(
                () -> new NotFoundException("Заказ с id: " + orderId + " не найден!")
        );
    }

    /**
     * Удаляет заказ из базы данных
     *
     * @param orderDto заказ, который необходимо удалить
     */
    @Override
    public void deleteOrder(OrderDto orderDto) {
        //TODO Возможно обрабатывать ошибку
        orderRepository.delete(orderMapper.orderDtoToOrder(orderDto));
    }

    /**
     * Добавляет заказ в базу данных
     *
     * @param orderDto заказ, который необходимо добавить
     * @return OrderDto, отображение добавленного заказа
     */
    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        Order orderToSave = orderMapper.orderDtoToOrder(orderDto);
        try {
            return orderMapper.orderToOrderDto(orderRepository.save(orderToSave));
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrationException("Попытка добавления заказа приводит к нарушению целостности данных");
        }
    }

    /**
     * Изменение существующего заказа
     *
     * @param orderDto заказ, который необходимо изменить
     */
    @Override
    public void updateOrder(OrderDto orderDto) {
        Optional<Order> foundOrder = orderRepository.findById(orderDto.getId());

        foundOrder.ifPresentOrElse(
              order -> orderRepository.save(
                       OrderMapper.updateOrderByNotNullFieldsOfOrderDto(order,orderDto)),
                () -> {
                    throw new NotFoundException("Клиент с id: " + orderDto.getId() + " не найден!");
                }
        );
    }

    /**
     * Поиск заказов, которые соответствуют заданному пользователю
     *
     * @param userId ID пользователя
     * @return List<OrderDto> заказов, которые соответствуют заданному пользователю
     */
    @Override
    public List<OrderDto> getOrdersByIdUser(Long userId) {
        return listOrderToListOrderDto(orderRepository.getOrderByUserId(userId));
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
