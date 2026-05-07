package com.example.demo.domain.order.events;

import com.example.demo.domain.common.DomainEvent;
import com.example.demo.domain.order.OrderId;

import java.time.Instant;

public record OrderDelivered(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {
}
