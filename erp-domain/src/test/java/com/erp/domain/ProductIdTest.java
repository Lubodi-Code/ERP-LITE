package com.erp.domain;

import com.erp.domain.product.ProductId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateProductId_whenValueIsNotNull() {
        UUID uuid = UUID.randomUUID();
        ProductId productId = new ProductId(uuid);

        assertEquals(uuid, productId.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(null));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateProductId_whenValidUuid() {
        UUID uuid = UUID.randomUUID();
        ProductId productId = ProductId.of(uuid);

        assertEquals(uuid, productId.value());
    }

    @Test
    void generate_shouldReturnProductIdWithNonNullUuid() {
        ProductId productId = ProductId.generate();

        assertNotNull(productId);
        assertNotNull(productId.value());
    }

    @Test
    void generate_shouldReturnDifferentIdsOnEachCall() {
        ProductId first = ProductId.generate();
        ProductId second = ProductId.generate();

        assertNotEquals(first, second);
    }
}
