package com.erp.domain;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.order.*;
import com.erp.domain.order.events.*;
import com.erp.domain.product.*;
import com.erp.domain.shared.CustomerId;
import com.erp.domain.shared.Money;
import com.erp.domain.shared.Quantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    private static Product createProduct(int stock, double price, Currency currency) {
        return Product.create(
                SKU.of("PROD-001"),
                ProductName.of("Test Product"),
                "Description",
                Money.of(price, currency),
                Stock.of(stock),
                CategoryReference.of("cat-test"),
                ProductImage.of("https://example.com/img.jpg"),
                "admin"
        );
    }

    private static OrderItem createOrderItem(int stock) {
        Product product = createProduct(stock, 100.00, USD);
        return OrderItem.from(product, Quantity.of(1));
    }

    private static Customer createCustomer() {
        return Customer.of(CustomerId.of(1L), "Test Customer");
    }

    private static Order createPendingOrder() {
        return Order.create(
                OrderNumber.of("ORD-2025-001"),
                createCustomer(),
                List.of(createOrderItem(10)),
                "admin"
        );
    }

    // -------------------------------------------------------------------------
    // create
    // -------------------------------------------------------------------------

    @Test
    void create_shouldCreatePendingOrder_whenValidArguments() {
        Order order = createPendingOrder();

        assertNotNull(order.getId());
        assertEquals("ORD-2025-001", order.getOrderNumber().value());
        assertTrue(order.getStatus().isPending());
        assertEquals(1, order.getItems().size());
        assertNotNull(order.getTotalAmount());
        assertNotNull(order.getAuditInfo());
    }

    @Test
    void create_shouldSetTotalAsItemSubtotal() {
        Order order = createPendingOrder();

        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(order.getTotalAmount().amount()));
    }

    @Test
    void create_shouldRegisterOrderCreatedEvent() {
        Order order = createPendingOrder();

        List<DomainEvent> events = order.pullDomainEvents();

        assertEquals(1, events.size());
        assertInstanceOf(OrderCreated.class, events.get(0));
    }

    @Test
    void create_shouldThrow_whenOrderNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Order.create(
                null, createCustomer(), List.of(createOrderItem(10)), "admin"));
    }

    @Test
    void create_shouldThrow_whenCustomerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Order.create(
                OrderNumber.of("ORD-2025-001"), null, List.of(createOrderItem(10)), "admin"));
    }

    @Test
    void create_shouldThrow_whenItemsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Order.create(
                OrderNumber.of("ORD-2025-001"), createCustomer(), null, "admin"));
    }

    @Test
    void create_shouldThrow_whenItemsIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Order.create(
                OrderNumber.of("ORD-2025-001"), createCustomer(), List.of(), "admin"));
    }

    @Test
    void create_shouldThrow_whenItemsHaveMixedCurrencies() {
        Product usdProduct = createProduct(10, 100.00, USD);
        Product eurProduct = createProduct(10, 50.00, EUR);
        OrderItem usdItem = OrderItem.from(usdProduct, Quantity.of(1));
        OrderItem eurItem = OrderItem.from(eurProduct, Quantity.of(1));

        assertThrows(IllegalArgumentException.class, () -> Order.create(
                OrderNumber.of("ORD-2025-001"), createCustomer(), List.of(usdItem, eurItem), "admin"));
    }

    // -------------------------------------------------------------------------
    // confirm
    // -------------------------------------------------------------------------

    @Test
    void confirm_shouldSetStatusToConfirmed_whenOrderIsPending() {
        Order order = createPendingOrder();
        order.pullDomainEvents();

        order.confirm();

        assertTrue(order.getStatus().isConfirmed());
    }

    @Test
    void confirm_shouldRegisterOrderConfirmedEvent() {
        Order order = createPendingOrder();
        order.pullDomainEvents();

        order.confirm();

        List<DomainEvent> events = order.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(OrderConfirmed.class, events.get(0));
    }

    @Test
    void confirm_shouldThrow_whenOrderIsNotPending() {
        Order order = createPendingOrder();
        order.confirm();

        assertThrows(IllegalStateException.class, order::confirm);
    }

    // -------------------------------------------------------------------------
    // ship
    // -------------------------------------------------------------------------

    @Test
    void ship_shouldSetStatusToShipped_whenOrderIsConfirmed() {
        Order order = createPendingOrder();
        order.confirm();
        order.pullDomainEvents();

        order.ship();

        assertTrue(order.getStatus().isShipped());
    }

    @Test
    void ship_shouldRegisterOrderShippedEvent() {
        Order order = createPendingOrder();
        order.confirm();
        order.pullDomainEvents();

        order.ship();

        List<DomainEvent> events = order.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(OrderShipped.class, events.get(0));
    }

    @Test
    void ship_shouldThrow_whenOrderIsNotConfirmed() {
        Order order = createPendingOrder();

        assertThrows(IllegalStateException.class, order::ship);
    }

    // -------------------------------------------------------------------------
    // deliver
    // -------------------------------------------------------------------------

    @Test
    void deliver_shouldSetStatusToDelivered_whenOrderIsShipped() {
        Order order = createPendingOrder();
        order.confirm();
        order.ship();
        order.pullDomainEvents();

        order.deliver();

        assertTrue(order.getStatus().isDelivered());
    }

    @Test
    void deliver_shouldRegisterOrderDeliveredEvent() {
        Order order = createPendingOrder();
        order.confirm();
        order.ship();
        order.pullDomainEvents();

        order.deliver();

        List<DomainEvent> events = order.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(OrderDelivered.class, events.get(0));
    }

    @Test
    void deliver_shouldThrow_whenOrderIsNotShipped() {
        Order order = createPendingOrder();
        order.confirm();

        assertThrows(IllegalStateException.class, order::deliver);
    }

    // -------------------------------------------------------------------------
    // cancel
    // -------------------------------------------------------------------------

    @Test
    void cancel_shouldSetStatusToCancelled_whenOrderIsPending() {
        Order order = createPendingOrder();
        order.pullDomainEvents();

        order.cancel("Customer request");

        assertTrue(order.getStatus().isCancelled());
    }

    @Test
    void cancel_shouldSetStatusToCancelled_whenOrderIsConfirmed() {
        Order order = createPendingOrder();
        order.confirm();
        order.pullDomainEvents();

        order.cancel("Out of stock");

        assertTrue(order.getStatus().isCancelled());
    }

    @Test
    void cancel_shouldRegisterOrderCancelledEvent() {
        Order order = createPendingOrder();
        order.pullDomainEvents();

        order.cancel("Customer request");

        List<DomainEvent> events = order.pullDomainEvents();
        assertEquals(1, events.size());
        OrderCancelled event = (OrderCancelled) events.get(0);
        assertEquals("Customer request", event.reason());
    }

    @Test
    void cancel_shouldThrow_whenReasonIsNull() {
        Order order = createPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.cancel(null));
    }

    @Test
    void cancel_shouldThrow_whenReasonIsBlank() {
        Order order = createPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.cancel("  "));
    }

    @Test
    void cancel_shouldThrow_whenOrderIsDelivered() {
        Order order = createPendingOrder();
        order.confirm();
        order.ship();
        order.deliver();

        assertThrows(IllegalStateException.class, () -> order.cancel("Too late"));
    }

    @Test
    void cancel_shouldThrow_whenOrderIsShipped() {
        Order order = createPendingOrder();
        order.confirm();
        order.ship();

        assertThrows(IllegalStateException.class, () -> order.cancel("Changed mind"));
    }

    // -------------------------------------------------------------------------
    // addItem
    // -------------------------------------------------------------------------

    @Test
    void addItem_shouldAddItemAndRecalculateTotal_whenOrderIsPending() {
        Order order = createPendingOrder();
        order.pullDomainEvents();
        OrderItem newItem = createOrderItem(5);

        order.addItem(newItem);

        assertEquals(2, order.getItems().size());
        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(order.getTotalAmount().amount()));
    }

    @Test
    void addItem_shouldThrow_whenItemIsNull() {
        Order order = createPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }

    @Test
    void addItem_shouldThrow_whenOrderIsNotPending() {
        Order order = createPendingOrder();
        order.confirm();
        OrderItem item = createOrderItem(5);

        assertThrows(IllegalStateException.class, () -> order.addItem(item));
    }

    // -------------------------------------------------------------------------
    // removeItem
    // -------------------------------------------------------------------------

    @Test
    void removeItem_shouldRemoveItemAndRecalculateTotal_whenOrderIsPending() {
        OrderItem item1 = createOrderItem(10);
        OrderItem item2 = createOrderItem(10);
        Order order = Order.create(
                OrderNumber.of("ORD-2025-001"),
                createCustomer(),
                List.of(item1, item2),
                "admin"
        );
        order.pullDomainEvents();

        order.removeItem(item1);

        assertEquals(1, order.getItems().size());
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(order.getTotalAmount().amount()));
    }

    @Test
    void removeItem_shouldThrow_whenItemIsNull() {
        Order order = createPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.removeItem(null));
    }

    @Test
    void removeItem_shouldThrow_whenOrderIsNotPending() {
        Order order = createPendingOrder();
        OrderItem item = order.getItems().get(0);
        order.confirm();

        assertThrows(IllegalStateException.class, () -> order.removeItem(item));
    }

    @Test
    void removeItem_shouldThrow_whenItemNotFoundInOrder() {
        Order order = createPendingOrder();
        OrderItem unrelatedItem = createOrderItem(5);

        assertThrows(IllegalArgumentException.class, () -> order.removeItem(unrelatedItem));
    }

    @Test
    void removeItem_shouldThrow_whenRemovingLastItem() {
        OrderItem item = createOrderItem(10);
        Order order = Order.create(
                OrderNumber.of("ORD-2025-001"),
                createCustomer(),
                List.of(item),
                "admin"
        );

        assertThrows(IllegalStateException.class, () -> order.removeItem(item));
    }

    // -------------------------------------------------------------------------
    // validateItems
    // -------------------------------------------------------------------------

    @Test
    void validateItems_shouldThrow_whenItemListIsEmpty() {
        Order order = createPendingOrder();

        order.getItems().clear();

        assertThrows(IllegalStateException.class, order::validateItems);
    }

    // -------------------------------------------------------------------------
    // calculateTotal
    // -------------------------------------------------------------------------

    @Test
    void calculateTotal_shouldReturnSumOfItemSubtotals() {
        OrderItem item1 = createOrderItem(10);
        OrderItem item2 = createOrderItem(10);
        Order order = Order.create(
                OrderNumber.of("ORD-2025-001"),
                createCustomer(),
                List.of(item1, item2),
                "admin"
        );

        Money total = order.calculateTotal();

        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(total.amount()));
    }
}
