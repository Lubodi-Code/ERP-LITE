package com.erp.domain.product.events;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.product.ProductId;

import java.time.Instant;

public record StockChanged(
        ProductId productId,
        Integer oldStock,
        Integer newStock,
        String reason,
        Instant timestamp
) implements DomainEvent {
}
