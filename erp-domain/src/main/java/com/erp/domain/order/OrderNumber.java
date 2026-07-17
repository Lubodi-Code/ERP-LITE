package com.erp.domain.order;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public record OrderNumber(String value) {

    private static final Pattern ORDER_NUMBER_PATTERN = Pattern.compile("^ORD-\\d{4}-\\d{3}$");

    public OrderNumber {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("OrderNumber must not be blank");
        if (!ORDER_NUMBER_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("OrderNumber must match pattern ORD-YYYY-NNN, got: " + value);
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    public static OrderNumber generate() {
        int year = LocalDate.now().getYear();
        int seq = ThreadLocalRandom.current().nextInt(1, 1000);
        return new OrderNumber(String.format("ORD-%d-%03d", year, seq));
    }
}
