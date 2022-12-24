package ru.ccfit.filedrop.mapper;

import org.mapstruct.Mapper;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);

    static Order updateOrderByNotNullFieldsOfOrderDto(Order order, OrderDto orderDto) {
        if (orderDto.getStatus() != null) {
            order.setStatus(orderDto.getStatus());
        }
        if (orderDto.getStoreUpTo() != null) {
            order.setStoreUpTo(orderDto.getStoreUpTo());
        }
        return order;
    }
}
