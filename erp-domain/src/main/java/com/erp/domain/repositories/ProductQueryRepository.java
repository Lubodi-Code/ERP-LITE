package com.erp.domain.repositories;

import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.Product;
import com.erp.domain.product.ProductId;
import com.erp.domain.shared.Money;

import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {

    List<Product> searchProducts(String query, CategoryReference category);

    Optional<Product> findProductView(ProductId id);

    List<Product> findByPriceRange(Money minPrice, Money maxPrice);
}
