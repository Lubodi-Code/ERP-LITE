package com.erp.domain.order.events;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.order.OrderId;
import com.erp.domain.shared.CustomerId;
import com.erp.domain.shared.Money;

import java.time.Instant;

public record OrderCreated(
        OrderId orderId,
        CustomerId customerId,
        String customerName,
        Money totalAmount,
        Instant timestamp
) implements DomainEvent {
}
