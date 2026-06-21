package com.erp.domain.repositories;

import com.erp.domain.entities.OrderRoot;
import com.erp.domain.order.OrderId;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.shared.CustomerId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    OrderRoot save(OrderRoot order);

    Optional<OrderRoot> findById(OrderId id);

    Optional<OrderRoot> findByOrderNumber(OrderNumber orderNumber);

    List<OrderRoot> findByCustomerId(CustomerId customerId);

    void delete(OrderId id);
}
