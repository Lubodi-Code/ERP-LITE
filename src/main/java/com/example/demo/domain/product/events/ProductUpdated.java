package com.example.demo.domain.product.events;

import com.example.demo.domain.common.DomainEvent;
import com.example.demo.domain.product.ProductId;

import java.time.Instant;

public record ProductUpdated(
        ProductId productId,
        Instant timestamp
) implements DomainEvent {
}
