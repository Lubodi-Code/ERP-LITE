package com.erp.infrastructure.persistence.mongo.repository;

import com.erp.domain.catalog.CatalogType;
import com.erp.infrastructure.persistence.mongo.document.CatalogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CatalogMongoRepository extends MongoRepository<CatalogDocument, String> {

    Optional<CatalogDocument> findByCatalogType(CatalogType catalogType);
}
