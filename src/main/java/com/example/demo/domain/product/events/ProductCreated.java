package com.example.demo.domain.product.events;

import com.example.demo.domain.common.DomainEvent;
import com.example.demo.domain.product.ProductId;
import com.example.demo.domain.product.ProductName;
import com.example.demo.domain.product.SKU;
import com.example.demo.domain.shared.Money;

import java.time.Instant;

public record ProductCreated(
        ProductId productId,
        SKU sku,
        ProductName name,
        Money price,
        Instant timestamp
) implements DomainEvent {
}
