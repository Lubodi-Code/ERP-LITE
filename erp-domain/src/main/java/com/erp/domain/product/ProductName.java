package com.erp.domain.product;

public record ProductName(String value) {

    public ProductName {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("ProductName must not be blank");
        if (value.length() < 3)
            throw new IllegalArgumentException("ProductName must have at least 3 characters");
        if (value.length() > 200)
            throw new IllegalArgumentException("ProductName must have at most 200 characters");
    }

    public static ProductName of(String value) {
        return new ProductName(value);
    }
}
