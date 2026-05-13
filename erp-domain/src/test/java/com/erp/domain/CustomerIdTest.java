package com.erp.domain;

import com.erp.domain.shared.CustomerId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIdTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateCustomerId_whenValueIsPositive() {
        CustomerId customerId = new CustomerId(1L);

        assertEquals(1L, customerId.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerId(null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerId(0L));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerId(-1L));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateCustomerId_whenValueIsPositive() {
        CustomerId customerId = CustomerId.of(42L);

        assertEquals(42L, customerId.value());
    }

    @Test
    void of_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CustomerId.of(null));
    }
}
