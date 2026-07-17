package com.erp.domain.vistas;

/**
 * Read model representation of Items.
 * Used for queries (CQRS read side).
 * This is a simplified view optimized for display.
 */
public record ItemsView(
    String code,
    String value,
    String description,
    Integer displayOrder
) {
}
