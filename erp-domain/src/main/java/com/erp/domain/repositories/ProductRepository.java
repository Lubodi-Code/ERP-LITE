package com.erp.domain.repositories;

import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.SKU;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    ProductRoot save(ProductRoot product);

    Optional<ProductRoot> findById(ProductId id);

    Optional<ProductRoot> findBySku(SKU sku);

    List<ProductRoot> findByCategory(CategoryReference category);

    void delete(ProductId id);
}
