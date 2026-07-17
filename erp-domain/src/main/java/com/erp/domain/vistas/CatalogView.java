package com.erp.domain.vistas;

import java.time.Instant;
import java.util.List;

import com.erp.domain.catalog.CatalogType;

/**
 * Read model representation of a Catalog.
 * Used for queries (CQRS read side).
 * This is a simplified view optimized for display.
 */
public record CatalogView(
    Boolean active,
    String name,
    String description,
    CatalogType type,
    Instant createdAt,
    Instant updatedAt,
    List<ItemsView> items
) {
}
