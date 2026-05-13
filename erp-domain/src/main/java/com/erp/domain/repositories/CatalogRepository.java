package com.erp.domain.repositories;

import com.erp.domain.catalog.Catalog;
import com.erp.domain.catalog.CatalogType;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    Optional<Catalog> findById(String id);

    Optional<Catalog> findByType(CatalogType catalogType);

    List<Catalog> findAll();

    List<Catalog> findAllActive();
}
