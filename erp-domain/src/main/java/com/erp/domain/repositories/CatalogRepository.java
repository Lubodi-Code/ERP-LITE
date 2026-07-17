package com.erp.domain.repositories;

import com.erp.domain.vistas.CatalogView;
import com.erp.domain.vistas.ItemsView;

import java.util.Optional;

/**
 * Repository for Catalog queries (CQRS read side).
 * Returns views optimized for display.
 */
public interface CatalogRepository {

    Optional<CatalogView> findByType(String type);

    Optional<ItemsView> findByTypeAndCode(String type, String code);
}
