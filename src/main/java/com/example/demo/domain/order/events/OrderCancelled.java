package com.example.demo.domain.order.events;

import com.example.demo.domain.common.DomainEvent;
import com.example.demo.domain.order.OrderId;

import java.time.Instant;

public record OrderCancelled(
        OrderId orderId,
        String reason,
        Instant timestamp
) implements DomainEvent {
}
