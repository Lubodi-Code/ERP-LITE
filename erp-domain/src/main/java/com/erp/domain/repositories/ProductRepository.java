package com.erp.domain.repositories;

import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.Product;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.SKU;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(ProductId id);

    Optional<Product> findBySku(SKU sku);

    List<Product> findByCategory(CategoryReference category);

    void delete(ProductId id);
}
