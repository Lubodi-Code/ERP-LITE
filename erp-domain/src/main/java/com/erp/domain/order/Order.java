package com.erp.domain.order;

import com.erp.domain.common.AggregateRoot;
import com.erp.domain.order.events.OrderCancelled;
import com.erp.domain.order.events.OrderConfirmed;
import com.erp.domain.order.events.OrderCreated;
import com.erp.domain.order.events.OrderDelivered;
import com.erp.domain.order.events.OrderShipped;
import com.erp.domain.shared.AuditInfo;
import com.erp.domain.shared.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order extends AggregateRoot<OrderId> {

    private OrderId id;
    private OrderNumber orderNumber;
    private Customer customer;
    private OrderStatus status;
    private List<OrderItem> items;
    private Money totalAmount;
    private AuditInfo auditInfo;

    Order(OrderId id, OrderNumber orderNumber, Customer customer, OrderStatus status,
          List<OrderItem> items, Money totalAmount, AuditInfo auditInfo) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.status = status;
        this.items = new ArrayList<>(items);
        this.totalAmount = totalAmount;
        this.auditInfo = auditInfo;
    }

    public static Order create(OrderNumber orderNumber, Customer customer,
                               List<OrderItem> items, String createdBy) {
        if (orderNumber == null) throw new IllegalArgumentException("OrderNumber must not be null");
        if (customer == null) throw new IllegalArgumentException("Customer must not be null");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");

        validateCurrencyConsistency(items);

        OrderId id = OrderId.generate();
        AuditInfo audit = AuditInfo.create(createdBy, Instant.now());
        Money total = calculateTotal(items);

        Order order = new Order(id, orderNumber, customer, OrderStatus.pending(), items, total, audit);
        order.registerEvent(new OrderCreated(
                id, customer.customerId(), customer.customerName(), total, audit.createdAt()));
        return order;
    }

    public void confirm() {
        validateTransition(OrderStatus.confirmed());
        this.status = OrderStatus.confirmed();
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new OrderConfirmed(this.id, Instant.now()));
    }

    public void ship() {
        validateTransition(OrderStatus.shipped());
        this.status = OrderStatus.shipped();
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new OrderShipped(this.id, Instant.now()));
    }

    public void deliver() {
        validateTransition(OrderStatus.delivered());
        this.status = OrderStatus.delivered();
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new OrderDelivered(this.id, Instant.now()));
    }

    public void cancel(String reason) {
        if (reason == null || reason.isBlank())
            throw new IllegalArgumentException("Cancellation reason must not be blank");
        validateTransition(OrderStatus.cancelled());
        this.status = OrderStatus.cancelled();
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new OrderCancelled(this.id, reason, Instant.now()));
    }

    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("OrderItem must not be null");
        if (!this.status.isPending())
            throw new IllegalStateException("Items can only be added to PENDING orders");
        this.items.add(item);
        this.totalAmount = calculateTotal(this.items);
        this.auditInfo = auditInfo.updateTimestamp();
    }

    public void removeItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("OrderItem must not be null");
        if (!this.status.isPending())
            throw new IllegalStateException("Items can only be removed from PENDING orders");
        boolean removed = this.items.remove(item);
        if (!removed) throw new IllegalArgumentException("Item not found in order");
        validateItems();
        this.totalAmount = calculateTotal(this.items);
        this.auditInfo = auditInfo.updateTimestamp();
    }

    public void validateItems() {
        if (this.items == null || this.items.isEmpty())
            throw new IllegalStateException("Order must have at least one item");
    }

    public void validateTransition(OrderStatus next) {
        if (!this.status.canTransitionTo(next))
            throw new IllegalStateException(
                    "Invalid transition from " + this.status.value() + " to " + next.value());
    }

    public Money calculateTotal() {
        return calculateTotal(this.items);
    }

    private static Money calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce((a, b) -> a.add(b))
                .orElseThrow(() -> new IllegalStateException("Cannot calculate total of empty items"));
    }

    private static void validateCurrencyConsistency(List<OrderItem> items) {
        Currency first = items.get(0).getUnitPrice().currency();
        for (OrderItem item : items) {
            if (!item.getUnitPrice().currency().equals(first))
                throw new IllegalArgumentException(
                        "All items in an order must have the same currency");
        }
    }
}
