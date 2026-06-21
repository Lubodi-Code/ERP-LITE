package com.erp.infrastructure.persistence.jpa.adapter;

import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.SKU;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.infrastructure.persistence.jpa.entity.ProductEntity;
import com.erp.infrastructure.persistence.jpa.repository.ProductJpaRepository;
import com.erp.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA adapter (driven/secondary) for the {@link ProductRepositoryPort} domain port.
 * Translates between the {@link ProductRoot} aggregate and the {@link ProductEntity}
 * relational model, delegating persistence to {@link ProductJpaRepository}.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryJpaAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Override
    public ProductRoot save(ProductRoot product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity saved = jpaRepository.save(entity);
        log.debug("Persisted product id={}, sku={}", saved.getId(), saved.getSku());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ProductRoot> findById(ProductId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<ProductRoot> findBySku(SKU sku) {
        return jpaRepository.findBySku(sku.value()).map(mapper::toDomain);
    }

    @Override
    public void delete(ProductId id) {
        jpaRepository.deleteById(id.value());
        log.debug("Deleted product id={}", id.value());
    }
}
