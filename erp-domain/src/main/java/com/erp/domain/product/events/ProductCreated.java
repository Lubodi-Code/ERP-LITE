package com.erp.domain.product.events;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.ProductName;
import com.erp.domain.product.SKU;
import com.erp.domain.shared.Money;

import java.time.Instant;

public record ProductCreated(
        ProductId productId,
        SKU sku,
        ProductName name,
        Money price,
        Instant timestamp
) implements DomainEvent {
}
