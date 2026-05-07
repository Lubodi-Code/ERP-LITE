package com.example.demo.domain.order.events;

import com.example.demo.domain.common.DomainEvent;
import com.example.demo.domain.order.OrderId;
import com.example.demo.domain.shared.CustomerId;
import com.example.demo.domain.shared.Money;

import java.time.Instant;

public record OrderCreated(
        OrderId orderId,
        CustomerId customerId,
        String customerName,
        Money totalAmount,
        Instant timestamp
) implements DomainEvent {
}
