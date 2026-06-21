package com.erp.infrastructure.persistence.jpa.adapter;

import com.erp.domain.entities.OrderRoot;
import com.erp.domain.order.OrderId;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.ports.repositories.OrderRepositoryPort;
import com.erp.domain.shared.CustomerId;
import com.erp.infrastructure.persistence.jpa.entity.OrderEntity;
import com.erp.infrastructure.persistence.jpa.repository.OrderJpaRepository;
import com.erp.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter (driven/secondary) for the {@link OrderRepositoryPort} domain port.
 * Translates between the {@link OrderRoot} aggregate and the {@link OrderEntity}
 * relational model, delegating persistence to {@link OrderJpaRepository}.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryJpaAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    @Override
    public OrderRoot save(OrderRoot order) {
        OrderEntity entity = mapper.toEntity(order);
        // Wire the inverse side: each line item must point back to its owning order,
        // otherwise the non-null order_id FK would be left unset on cascade insert.
        entity.getItems().forEach(item -> item.setOrder(entity));
        OrderEntity saved = jpaRepository.save(entity);
        log.debug("Persisted order id={}, number={}", saved.getId(), saved.getOrderNumber());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<OrderRoot> findById(OrderId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<OrderRoot> findByOrderNumber(OrderNumber orderNumber) {
        return jpaRepository.findByOrderNumber(orderNumber.value()).map(mapper::toDomain);
    }

    @Override
    public List<OrderRoot> findByCustomerId(CustomerId customerId) {
        return jpaRepository.findByCustomerId(customerId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(OrderId id) {
        jpaRepository.deleteById(id.value());
        log.debug("Deleted order id={}", id.value());
    }
}
