package ru.ccfit.filedrop.service.interfaces;

import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.exception.*;
import java.util.List;

public interface OrderService {
    /**
     * Нахождение нужного заказа по его ID
     *
     * @param orderId заказ, который необходимо найти
     * @return OrderDto найденный заказ
     * @throws NotFoundException заказ не найден
     */
    OrderDto getOrderById(Long orderId);
    /**
     * Удаляет заказ из базы данных
     *
     * @param orderId id заказа, который необходимо удалить
     * @throws NotFoundException заказ не найден
     */
    void deleteOrder(Long orderId);
    /**
     * Добавляет заказ в базу данных
     *
     * @param orderDto заказ, который необходимо добавить
     * @return OrderDto, отображение добавленного заказа
     * @throws IntegrationException невозможно добавить файл
     */
    OrderDto addOrder(OrderDto orderDto);
    /**
     * Изменение существующего заказа
     *
     * @param orderDto заказ, который необходимо изменить
     * @throws NotFoundException заказ не найден
     */
    void updateOrder(Long orderId, OrderDto orderDto);
    /**
     * Поиск заказов, которые соответствуют заданному пользователю
     *
     * @param userId ID пользователя
     * @return List<OrderDto> заказов, которые соответствуют заданному пользователю
     */
    List<OrderDto> getOrdersByIdUser(Long userId);

    List<OrderDto> getAllOrders();
}
