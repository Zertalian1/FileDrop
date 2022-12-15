package ru.ccfit.filedrop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ccfit.filedrop.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    List<Order> getOrderByUserId(Long id);
}
