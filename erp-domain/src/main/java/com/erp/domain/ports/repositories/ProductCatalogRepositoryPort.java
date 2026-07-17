package com.erp.domain.ports.repositories;

import java.util.List;
import java.util.Optional;

import com.erp.domain.vistas.ProductView;

/**
 * Port read-only for Product queries in catalog.
 * Used for CQRS read side - optimized queries returning views.
 */
public interface ProductCatalogRepositoryPort {

    Optional<ProductView> findById(String id);

    Optional<ProductView> findBySku(String sku);

    List<ProductView> findByText(String text);

    List<ProductView> findByCategory(String category);

    List<ProductView> findActive();
}
