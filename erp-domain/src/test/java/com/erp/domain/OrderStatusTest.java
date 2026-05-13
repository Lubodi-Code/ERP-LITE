package com.erp.domain;

import com.erp.domain.order.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateOrderStatus_whenValidValue() {
        OrderStatus status = new OrderStatus("PENDING");

        assertEquals("PENDING", status.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new OrderStatus(null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new OrderStatus("  "));
    }

    @Test
    void constructor_shouldThrow_whenValueIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new OrderStatus("UNKNOWN"));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateStatus_whenValidValue() {
        assertEquals("PENDING", OrderStatus.of("PENDING").value());
    }

    @Test
    void pending_shouldReturnPendingStatus() {
        assertTrue(OrderStatus.pending().isPending());
    }

    @Test
    void confirmed_shouldReturnConfirmedStatus() {
        assertTrue(OrderStatus.confirmed().isConfirmed());
    }

    @Test
    void shipped_shouldReturnShippedStatus() {
        assertTrue(OrderStatus.shipped().isShipped());
    }

    @Test
    void delivered_shouldReturnDeliveredStatus() {
        assertTrue(OrderStatus.delivered().isDelivered());
    }

    @Test
    void cancelled_shouldReturnCancelledStatus() {
        assertTrue(OrderStatus.cancelled().isCancelled());
    }

    // -------------------------------------------------------------------------
    // canTransitionTo — from PENDING
    // -------------------------------------------------------------------------

    @Test
    void canTransitionTo_shouldAllowPendingToConfirmed() {
        assertTrue(OrderStatus.pending().canTransitionTo(OrderStatus.confirmed()));
    }

    @Test
    void canTransitionTo_shouldAllowPendingToCancelled() {
        assertTrue(OrderStatus.pending().canTransitionTo(OrderStatus.cancelled()));
    }

    @Test
    void canTransitionTo_shouldDenyPendingToPending() {
        assertFalse(OrderStatus.pending().canTransitionTo(OrderStatus.pending()));
    }

    @Test
    void canTransitionTo_shouldDenyPendingToShipped() {
        assertFalse(OrderStatus.pending().canTransitionTo(OrderStatus.shipped()));
    }

    @Test
    void canTransitionTo_shouldDenyPendingToDelivered() {
        assertFalse(OrderStatus.pending().canTransitionTo(OrderStatus.delivered()));
    }

    // -------------------------------------------------------------------------
    // canTransitionTo — from CONFIRMED
    // -------------------------------------------------------------------------

    @Test
    void canTransitionTo_shouldAllowConfirmedToShipped() {
        assertTrue(OrderStatus.confirmed().canTransitionTo(OrderStatus.shipped()));
    }

    @Test
    void canTransitionTo_shouldAllowConfirmedToCancelled() {
        assertTrue(OrderStatus.confirmed().canTransitionTo(OrderStatus.cancelled()));
    }

    @Test
    void canTransitionTo_shouldDenyConfirmedToPending() {
        assertFalse(OrderStatus.confirmed().canTransitionTo(OrderStatus.pending()));
    }

    @Test
    void canTransitionTo_shouldDenyConfirmedToConfirmed() {
        assertFalse(OrderStatus.confirmed().canTransitionTo(OrderStatus.confirmed()));
    }

    @Test
    void canTransitionTo_shouldDenyConfirmedToDelivered() {
        assertFalse(OrderStatus.confirmed().canTransitionTo(OrderStatus.delivered()));
    }

    // -------------------------------------------------------------------------
    // canTransitionTo — from SHIPPED
    // -------------------------------------------------------------------------

    @Test
    void canTransitionTo_shouldAllowShippedToDelivered() {
        assertTrue(OrderStatus.shipped().canTransitionTo(OrderStatus.delivered()));
    }

    @Test
    void canTransitionTo_shouldDenyShippedToCancelled() {
        assertFalse(OrderStatus.shipped().canTransitionTo(OrderStatus.cancelled()));
    }

    @Test
    void canTransitionTo_shouldDenyShippedToPending() {
        assertFalse(OrderStatus.shipped().canTransitionTo(OrderStatus.pending()));
    }

    // -------------------------------------------------------------------------
    // canTransitionTo — from DELIVERED (final state)
    // -------------------------------------------------------------------------

    @Test
    void canTransitionTo_shouldDenyDeliveredToAnyStatus() {
        assertFalse(OrderStatus.delivered().canTransitionTo(OrderStatus.pending()));
        assertFalse(OrderStatus.delivered().canTransitionTo(OrderStatus.confirmed()));
        assertFalse(OrderStatus.delivered().canTransitionTo(OrderStatus.shipped()));
        assertFalse(OrderStatus.delivered().canTransitionTo(OrderStatus.cancelled()));
    }

    // -------------------------------------------------------------------------
    // canTransitionTo — from CANCELLED (final state)
    // -------------------------------------------------------------------------

    @Test
    void canTransitionTo_shouldDenyCancelledToAnyStatus() {
        assertFalse(OrderStatus.cancelled().canTransitionTo(OrderStatus.pending()));
        assertFalse(OrderStatus.cancelled().canTransitionTo(OrderStatus.confirmed()));
        assertFalse(OrderStatus.cancelled().canTransitionTo(OrderStatus.shipped()));
        assertFalse(OrderStatus.cancelled().canTransitionTo(OrderStatus.delivered()));
    }

    // -------------------------------------------------------------------------
    // isFinalState
    // -------------------------------------------------------------------------

    @Test
    void isFinalState_shouldReturnTrue_whenDelivered() {
        assertTrue(OrderStatus.delivered().isFinalState());
    }

    @Test
    void isFinalState_shouldReturnTrue_whenCancelled() {
        assertTrue(OrderStatus.cancelled().isFinalState());
    }

    @Test
    void isFinalState_shouldReturnFalse_whenPending() {
        assertFalse(OrderStatus.pending().isFinalState());
    }

    @Test
    void isFinalState_shouldReturnFalse_whenConfirmed() {
        assertFalse(OrderStatus.confirmed().isFinalState());
    }

    @Test
    void isFinalState_shouldReturnFalse_whenShipped() {
        assertFalse(OrderStatus.shipped().isFinalState());
    }
}
