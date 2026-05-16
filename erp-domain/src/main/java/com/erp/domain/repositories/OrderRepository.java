package com.erp.domain.repositories;

import com.erp.domain.order.Order;
import com.erp.domain.order.OrderId;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.shared.CustomerId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId id);

    Optional<Order> findByOrderNumber(OrderNumber orderNumber);

    List<Order> findByCustomerId(CustomerId customerId);

    void delete(OrderId id);
}
