package com.erp.infrastructure.persistence.jpa.repository;

import com.erp.infrastructure.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findBySku(String sku);
}
