package com.erp.domain.product.events;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.product.ProductId;

import java.time.Instant;

public record ProductDeactivated(
        ProductId productId,
        Instant timestamp
) implements DomainEvent {
}
