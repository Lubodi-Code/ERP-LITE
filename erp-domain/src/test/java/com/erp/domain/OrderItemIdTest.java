package com.erp.domain;

import com.erp.domain.order.OrderItemId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemIdTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateOrderItemId_whenValueIsNotNull() {
        UUID uuid = UUID.randomUUID();
        OrderItemId id = new OrderItemId(uuid);

        assertEquals(uuid, id.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItemId(null));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateOrderItemId_whenValidUuid() {
        UUID uuid = UUID.randomUUID();
        OrderItemId id = OrderItemId.of(uuid);

        assertEquals(uuid, id.value());
    }

    @Test
    void generate_shouldReturnOrderItemIdWithNonNullUuid() {
        OrderItemId id = OrderItemId.generate();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    void generate_shouldReturnDifferentIdsOnEachCall() {
        OrderItemId first = OrderItemId.generate();
        OrderItemId second = OrderItemId.generate();

        assertNotEquals(first, second);
    }
}
