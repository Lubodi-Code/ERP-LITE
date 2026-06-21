package com.erp.domain.ports.repositories;

import java.util.List;
import java.util.Optional;

import com.erp.domain.entities.OrderRoot;
import com.erp.domain.order.OrderId;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.shared.CustomerId;

/**
 * Port (domain side) for Order persistence.
 * The infrastructure layer provides the real adapter (JPA, Mongo, ...).
 */
public interface OrderRepositoryPort {

    OrderRoot save(OrderRoot order);

    Optional<OrderRoot> findById(OrderId id);

    Optional<OrderRoot> findByOrderNumber(OrderNumber orderNumber);

    List<OrderRoot> findByCustomerId(CustomerId customerId);

    void delete(OrderId id);
}
