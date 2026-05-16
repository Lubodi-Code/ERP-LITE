package com.erp.infrastructure.persistence.mongo.repository;

import com.erp.infrastructure.persistence.mongo.document.ProductInCatalogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInCatalogMongoRepository extends MongoRepository<ProductInCatalogDocument, String> {

    Optional<ProductInCatalogDocument> findBySku(String sku);

    List<ProductInCatalogDocument> findByCategoryId(String categoryId);

    List<ProductInCatalogDocument> findByActiveTrue();
}
