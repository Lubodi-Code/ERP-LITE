package com.erp.domain.product.events;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.ProductImage;

import java.time.Instant;

public record ProductImageUploaded(
        ProductId productId,
        ProductImage image,
        Instant timestamp
) implements DomainEvent {
}
