package com.example.demo.domain.product.events;

import com.example.demo.domain.common.DomainEvent;
import com.example.demo.domain.product.ProductId;

import java.time.Instant;

public record StockChanged(
        ProductId productId,
        Integer oldStock,
        Integer newStock,
        String reason,
        Instant timestamp
) implements DomainEvent {
}
