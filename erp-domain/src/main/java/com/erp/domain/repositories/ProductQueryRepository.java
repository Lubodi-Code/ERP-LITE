package com.erp.domain.repositories;

import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.ProductId;
import com.erp.domain.shared.Money;

import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {

    List<ProductRoot> searchProducts(String query, CategoryReference category);

    Optional<ProductRoot> findProductView(ProductId id);

    List<ProductRoot> findByPriceRange(Money minPrice, Money maxPrice);
}
