package com.erp.domain.repositories;

import com.erp.domain.catalog.CatalogType;
import com.erp.domain.entities.CatalogRoot;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    Optional<CatalogRoot> findById(String id);

    Optional<CatalogRoot> findByType(CatalogType catalogType);

    List<CatalogRoot> findAll();

    List<CatalogRoot> findAllActive();
}
