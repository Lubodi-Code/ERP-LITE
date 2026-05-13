package com.erp.domain;

import com.erp.domain.order.OrderNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderNumberTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateOrderNumber_whenValidPattern() {
        OrderNumber number = new OrderNumber("ORD-2025-001");

        assertEquals("ORD-2025-001", number.value());
    }

    @Test
    void constructor_shouldCreateOrderNumber_whenHighSequence() {
        assertDoesNotThrow(() -> new OrderNumber("ORD-2025-999"));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new OrderNumber(null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new OrderNumber("  "));
    }

    @Test
    void constructor_shouldThrow_whenMissingOrdPrefix() {
        assertThrows(IllegalArgumentException.class, () -> new OrderNumber("INV-2025-001"));
    }

    @Test
    void constructor_shouldThrow_whenYearHasThreeDigits() {
        assertThrows(IllegalArgumentException.class, () -> new OrderNumber("ORD-202-001"));
    }

    @Test
    void constructor_shouldThrow_whenSequenceHasTwoDigits() {
        assertThrows(IllegalArgumentException.class, () -> new OrderNumber("ORD-2025-01"));
    }

    @Test
    void constructor_shouldThrow_whenSequenceHasFourDigits() {
        assertThrows(IllegalArgumentException.class, () -> new OrderNumber("ORD-2025-0001"));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateOrderNumber_whenValidPattern() {
        OrderNumber number = OrderNumber.of("ORD-2024-042");

        assertEquals("ORD-2024-042", number.value());
    }

    @Test
    void of_shouldThrow_whenInvalidPattern() {
        assertThrows(IllegalArgumentException.class, () -> OrderNumber.of("INVALID"));
    }

    @Test
    void generate_shouldReturnValidOrderNumber() {
        OrderNumber number = OrderNumber.generate();

        assertNotNull(number);
        assertTrue(number.value().matches("^ORD-\\d{4}-\\d{3}$"));
    }
}
