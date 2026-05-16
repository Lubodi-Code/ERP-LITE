package com.erp.infrastructure.persistence.jpa.repository;

import com.erp.infrastructure.persistence.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    List<OrderEntity> findByCustomerId(Long customerId);
}
