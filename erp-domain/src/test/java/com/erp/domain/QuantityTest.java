package com.erp.domain;

import com.erp.domain.shared.Quantity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateQuantity_whenValueIsPositive() {
        Quantity quantity = new Quantity(5);

        assertEquals(5, quantity.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(0));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(-1));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateQuantity_whenValueIsPositive() {
        Quantity quantity = Quantity.of(10);

        assertEquals(10, quantity.value());
    }

    @Test
    void of_shouldThrow_whenValueIsZero() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(0));
    }
}
