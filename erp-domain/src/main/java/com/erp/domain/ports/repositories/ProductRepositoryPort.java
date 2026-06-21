package com.erp.domain.ports.repositories;

import java.util.Optional;

import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.SKU;

/**
 * Port (domain side) for Product persistence.
 * The infrastructure layer provides the real adapter (JPA, Mongo, ...).
 */
public interface ProductRepositoryPort {

    ProductRoot save(ProductRoot product);

    Optional<ProductRoot> findById(ProductId id);

    Optional<ProductRoot> findBySku(SKU sku);

    void delete(ProductId id);
}
