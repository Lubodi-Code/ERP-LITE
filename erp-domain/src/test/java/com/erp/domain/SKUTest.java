package com.erp.domain;

import com.erp.domain.product.SKU;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SKUTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateSKU_whenValidPattern() {
        SKU sku = new SKU("LAPTOP-001");

        assertEquals("LAPTOP-001", sku.value());
    }

    @Test
    void constructor_shouldCreateSKU_whenSingleLetterPrefix() {
        assertDoesNotThrow(() -> new SKU("A-999"));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new SKU(null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new SKU("  "));
    }

    @Test
    void constructor_shouldThrow_whenLowercaseLetters() {
        assertThrows(IllegalArgumentException.class, () -> new SKU("laptop-001"));
    }

    @Test
    void constructor_shouldThrow_whenMissingDash() {
        assertThrows(IllegalArgumentException.class, () -> new SKU("LAPTOP001"));
    }

    @Test
    void constructor_shouldThrow_whenDigitsInPrefix() {
        assertThrows(IllegalArgumentException.class, () -> new SKU("123-001"));
    }

    @Test
    void constructor_shouldThrow_whenSequenceHasTwoDigits() {
        assertThrows(IllegalArgumentException.class, () -> new SKU("LAPTOP-01"));
    }

    @Test
    void constructor_shouldThrow_whenSequenceHasFourDigits() {
        assertThrows(IllegalArgumentException.class, () -> new SKU("LAPTOP-0001"));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateSKU_whenValidPattern() {
        SKU sku = SKU.of("PHONE-042");

        assertEquals("PHONE-042", sku.value());
    }

    @Test
    void of_shouldThrow_whenInvalidPattern() {
        assertThrows(IllegalArgumentException.class, () -> SKU.of("invalid"));
    }
}
