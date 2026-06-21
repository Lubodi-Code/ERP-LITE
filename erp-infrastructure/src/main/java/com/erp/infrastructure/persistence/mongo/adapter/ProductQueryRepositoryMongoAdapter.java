package com.erp.infrastructure.persistence.mongo.adapter;

import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.ProductId;
import com.erp.domain.repositories.ProductQueryRepository;
import com.erp.domain.shared.Money;
import com.erp.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.erp.infrastructure.persistence.mongo.document.ProductInCatalogDocument;
import com.erp.infrastructure.persistence.mongo.repository.ProductInCatalogMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * MongoDB read-model adapter (driven/secondary) for the {@link ProductQueryRepository}
 * domain port. Serves the denormalized {@link ProductInCatalogDocument} read model
 * (CQRS query side) and maps it back to the {@link ProductRoot} aggregate view.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryMongoAdapter implements ProductQueryRepository {

    private final ProductInCatalogMongoRepository mongoRepository;
    private final ProductDocumentMapper mapper;

    @Override
    public List<ProductRoot> searchProducts(String query, CategoryReference category) {
        List<ProductInCatalogDocument> base = category != null
                ? mongoRepository.findByCategoryId(category.categoryId())
                : mongoRepository.findByActiveTrue();

        // Name filtering is done in-memory for now; promote to a Mongo text/regex
        // query if the read model grows large.
        return base.stream()
                .filter(doc -> query == null || query.isBlank()
                        || doc.getName().toLowerCase().contains(query.toLowerCase()))
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ProductRoot> findProductView(ProductId id) {
        return mongoRepository.findById(id.value().toString()).map(mapper::toDomain);
    }

    @Override
    public List<ProductRoot> findByPriceRange(Money minPrice, Money maxPrice) {
        BigDecimal min = minPrice.amount();
        BigDecimal max = maxPrice.amount();
        return mongoRepository.findByActiveTrue().stream()
                .filter(doc -> doc.getPrice().compareTo(min) >= 0
                        && doc.getPrice().compareTo(max) <= 0)
                .map(mapper::toDomain)
                .toList();
    }
}
