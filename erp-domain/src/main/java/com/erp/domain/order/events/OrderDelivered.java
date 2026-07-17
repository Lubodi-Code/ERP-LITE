package com.erp.domain.order.events;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.order.OrderId;

import java.time.Instant;

public record OrderDelivered(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {
}
