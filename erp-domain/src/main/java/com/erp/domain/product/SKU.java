package com.erp.domain.product;

import java.util.regex.Pattern;

public record SKU(String value) {

    private static final Pattern SKU_PATTERN = Pattern.compile("^[A-Z]+-\\d{3}$");

    public SKU {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("SKU must not be blank");
        if (!SKU_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("SKU must match pattern [A-Z]+-NNN, got: " + value);
    }

    public static SKU of(String value) {
        return new SKU(value);
    }
}
