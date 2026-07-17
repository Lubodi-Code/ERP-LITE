package com.erp.application.usecases.order;

import com.erp.application.command.order.CancelOrderCommand;
import com.erp.application.exceptions.CommandException;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.OrderRoot;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.order.Customer;
import com.erp.domain.order.OrderId;
import com.erp.domain.order.OrderItem;
import com.erp.domain.order.OrderItemId;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.order.OrderStatus;
import com.erp.domain.ports.repositories.OrderRepositoryPort;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.SKU;
import com.erp.domain.shared.AuditInfo;
import com.erp.domain.shared.CustomerId;
import com.erp.domain.shared.Money;
import com.erp.domain.shared.Quantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Demonstrates the CancelOrder flow with hand-rolled fake ports (no Spring, no Mockito):
 * happy path, order-not-found, and an invalid transition (already delivered).
 */
class CancelOrderTest {

    @Test
    void cancels_a_confirmed_order() {
        FakeOrderRepository orders = new FakeOrderRepository();
        OrderRoot order = orderWithStatus(OrderStatus.confirmed());
        orders.save(order);
        CancelOrder useCase = newUseCase(orders);

        OrderRoot result = useCase.execute(
                new CancelOrderCommand(order.getId().value().toString(), "Customer changed their mind"));

        assertEquals("CANCELLED", result.getStatus().value());
    }

    @Test
    void fails_when_order_not_found() {
        FakeOrderRepository orders = new FakeOrderRepository();
        CancelOrder useCase = newUseCase(orders);

        CancelOrderCommand cmd =
                new CancelOrderCommand(UUID.randomUUID().toString(), "Some perfectly valid reason");

        CommandException ex = assertThrows(CommandException.class, () -> useCase.execute(cmd));
        assertTrue(ex.getMessage().contains("Order not found"));
    }

    @Test
    void fails_when_order_already_delivered() {
        FakeOrderRepository orders = new FakeOrderRepository();
        OrderRoot order = orderWithStatus(OrderStatus.delivered());
        orders.save(order);
        CancelOrder useCase = newUseCase(orders);

        CancelOrderCommand cmd =
                new CancelOrderCommand(order.getId().value().toString(), "Trying to cancel a delivered order");

        // The domain forbids the transition; the use case lets it propagate (no blanket catch).
        assertThrows(IllegalStateException.class, () -> useCase.execute(cmd));
    }

    // --- helpers -----------------------------------------------------------

    private CancelOrder newUseCase(FakeOrderRepository orders) {
        CommandHelper helper = new CommandHelper(orders, new FakeProductRepository());
        return new CancelOrder(orders, helper);
    }

    private OrderRoot orderWithStatus(OrderStatus status) {
        Money price = Money.of(new BigDecimal("100.00"), Currency.getInstance("USD"));
        OrderItem item = OrderItem.rehydrate(
                OrderItemId.generate(), ProductId.generate(), "Laptop",
                Quantity.of(1), price, price);
        Instant now = Instant.now();
        return OrderRoot.rehydrate(
                OrderId.generate(), OrderNumber.generate(),
                Customer.of(CustomerId.of(5L), "Chelsey"),
                status, List.of(item), price,
                new AuditInfo("tester", now, now));
    }

    /** In-memory order store. */
    static class FakeOrderRepository implements OrderRepositoryPort {
        final Map<UUID, OrderRoot> store = new HashMap<>();
        public OrderRoot save(OrderRoot o) { store.put(o.getId().value(), o); return o; }
        public Optional<OrderRoot> findById(OrderId id) { return Optional.ofNullable(store.get(id.value())); }
        public Optional<OrderRoot> findByOrderNumber(OrderNumber n) { return Optional.empty(); }
        public List<OrderRoot> findByCustomerId(CustomerId c) { return List.of(); }
        public void delete(OrderId id) { store.remove(id.value()); }
    }

    /** Unused by these tests, but CommandHelper requires it. */
    static class FakeProductRepository implements ProductRepositoryPort {
        public ProductRoot save(ProductRoot p) { return p; }
        public Optional<ProductRoot> findById(ProductId id) { return Optional.empty(); }
        public Optional<ProductRoot> findBySku(SKU sku) { return Optional.empty(); }
        public void delete(ProductId id) {}
    }
}
