package ru.ccfit.filedrop.mapper;

import org.mapstruct.Mapper;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);
}
