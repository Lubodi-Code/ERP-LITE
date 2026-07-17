package com.erp.domain.catalog;

import java.util.Arrays;

/**
 * Types of catalogs available in the system.
 * Simplified enum for CQRS read models.
 */
public enum CatalogType {
    PRODUCT_CATEGORIES("Product Categories"),
    ORDER_STATUSES("Order Statuses"),
    PAYMENT_METHODS("Payment Methods"),
    SHIPPING_METHODS("Shipping Methods"),
    COUNTRIES("Countries"),
    CURRENCIES("Currencies");

    private final String displayName;

    CatalogType(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return name();
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CatalogType fromCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Catalog type code cannot be null or blank");
        }
        try {
            return valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown catalog type: " + code);
        }
    }

    public static boolean isValid(String code) {
        if (code == null || code.isBlank()) {
            return false;
        }
        return Arrays.stream(values())
                .anyMatch(t -> t.name().equals(code));
    }
}