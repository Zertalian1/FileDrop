package ru.ccfit.filedrop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ccfit.filedrop.entity.Order;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
