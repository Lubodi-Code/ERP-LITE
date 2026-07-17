package com.erp.domain.order;

import java.util.Set;

public record OrderStatus(String value) {

    private static final String PENDING_VALUE = "PENDING";
    private static final String CONFIRMED_VALUE = "CONFIRMED";
    private static final String SHIPPED_VALUE = "SHIPPED";
    private static final String DELIVERED_VALUE = "DELIVERED";
    private static final String CANCELLED_VALUE = "CANCELLED";

    private static final Set<String> VALID_VALUES =
            Set.of(PENDING_VALUE, CONFIRMED_VALUE, SHIPPED_VALUE, DELIVERED_VALUE, CANCELLED_VALUE);

    public OrderStatus {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("OrderStatus value must not be blank");
        if (!VALID_VALUES.contains(value))
            throw new IllegalArgumentException(
                    "Invalid OrderStatus: " + value + ". Valid values: " + VALID_VALUES);
    }

    public static OrderStatus of(String value) {
        return new OrderStatus(value);
    }

    public static OrderStatus pending() {
        return new OrderStatus(PENDING_VALUE);
    }

    public static OrderStatus confirmed() {
        return new OrderStatus(CONFIRMED_VALUE);
    }

    public static OrderStatus shipped() {
        return new OrderStatus(SHIPPED_VALUE);
    }

    public static OrderStatus delivered() {
        return new OrderStatus(DELIVERED_VALUE);
    }

    public static OrderStatus cancelled() {
        return new OrderStatus(CANCELLED_VALUE);
    }

    public boolean canTransitionTo(OrderStatus next) {
        return switch (this.value) {
            case PENDING_VALUE -> next.isPending() == false &&
                    (next.isConfirmed() || next.isCancelled());
            case CONFIRMED_VALUE -> next.isShipped() || next.isCancelled();
            case SHIPPED_VALUE -> next.isDelivered();
            default -> false; // DELIVERED and CANCELLED are final states
        };
    }

    public boolean isPending() {
        return PENDING_VALUE.equals(this.value);
    }

    public boolean isConfirmed() {
        return CONFIRMED_VALUE.equals(this.value);
    }

    public boolean isShipped() {
        return SHIPPED_VALUE.equals(this.value);
    }

    public boolean isDelivered() {
        return DELIVERED_VALUE.equals(this.value);
    }

    public boolean isCancelled() {
        return CANCELLED_VALUE.equals(this.value);
    }

    public boolean isFinalState() {
        return isDelivered() || isCancelled();
    }
}
