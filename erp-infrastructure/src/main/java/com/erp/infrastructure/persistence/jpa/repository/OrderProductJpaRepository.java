package com.erp.infrastructure.persistence.jpa.repository;

import com.erp.infrastructure.persistence.jpa.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductJpaRepository extends JpaRepository<OrderProductEntity, UUID> {

    List<OrderProductEntity> findByOrderId(UUID orderId);
}
