package com.erp.domain;

import com.erp.domain.order.OrderId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderIdTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateOrderId_whenValueIsNotNull() {
        UUID uuid = UUID.randomUUID();
        OrderId orderId = new OrderId(uuid);

        assertEquals(uuid, orderId.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(null));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateOrderId_whenValidUuid() {
        UUID uuid = UUID.randomUUID();
        OrderId orderId = OrderId.of(uuid);

        assertEquals(uuid, orderId.value());
    }

    @Test
    void generate_shouldReturnOrderIdWithNonNullUuid() {
        OrderId orderId = OrderId.generate();

        assertNotNull(orderId);
        assertNotNull(orderId.value());
    }

    @Test
    void generate_shouldReturnDifferentIdsOnEachCall() {
        OrderId first = OrderId.generate();
        OrderId second = OrderId.generate();

        assertNotEquals(first, second);
    }
}
