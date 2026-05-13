package com.erp.domain;

import com.erp.domain.product.ProductName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateProductName_whenValidLength() {
        ProductName name = new ProductName("Laptop");

        assertEquals("Laptop", name.value());
    }

    @Test
    void constructor_shouldCreateProductName_whenExactlyThreeChars() {
        assertDoesNotThrow(() -> new ProductName("ABC"));
    }

    @Test
    void constructor_shouldCreateProductName_whenExactly200Chars() {
        String name = "A".repeat(200);
        assertDoesNotThrow(() -> new ProductName(name));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ProductName(null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ProductName("  "));
    }

    @Test
    void constructor_shouldThrow_whenLessThanThreeChars() {
        assertThrows(IllegalArgumentException.class, () -> new ProductName("AB"));
    }

    @Test
    void constructor_shouldThrow_whenMoreThan200Chars() {
        String name = "A".repeat(201);
        assertThrows(IllegalArgumentException.class, () -> new ProductName(name));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateProductName_whenValidValue() {
        ProductName name = ProductName.of("Wireless Mouse");

        assertEquals("Wireless Mouse", name.value());
    }
}
