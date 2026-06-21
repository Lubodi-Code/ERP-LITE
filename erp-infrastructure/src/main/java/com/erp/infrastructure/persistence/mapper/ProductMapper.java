package com.erp.infrastructure.persistence.mapper;

import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.ProductImage;
import com.erp.domain.product.ProductName;
import com.erp.domain.product.SKU;
import com.erp.domain.product.Stock;
import com.erp.domain.shared.AuditInfo;
import com.erp.domain.shared.Money;
import com.erp.infrastructure.persistence.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    default ProductRoot toDomain(ProductEntity entity) {
        return ProductRoot.rehydrate(
                toProductId(entity.getId()),
                toSku(entity.getSku()),
                toName(entity.getName()),
                entity.getDescription(),
                toMoney(entity.getPrice(), entity.getCurrency()),
                toStock(entity.getStock()),
                toCategory(entity.getCategoryId()),
                toImage(entity.getImageUrl()),
                entity.isActive(),
                toAudit(null, entity.getCreatedAt(), entity.getUpdatedAt())
        );
    }

    default ProductEntity toEntity(ProductRoot product) {
        return ProductEntity.builder()
                .id(fromProductId(product.getId()))
                .sku(fromSku(product.getSku()))
                .name(fromName(product.getName()))
                .description(product.getDescription())
                .price(fromMoneyAmount(product.getPrice()))
                .currency(fromMoneyCurrency(product.getPrice()))
                .stock(fromStock(product.getStock()))
                .categoryId(fromCategory(product.getCategory()))
                .imageUrl(fromImage(product.getImage()))
                .active(product.isActive())
                .createdAt(product.getAuditInfo().createdAt())
                .updatedAt(product.getAuditInfo().updatedAt())
                .build();
    }

    default ProductId toProductId(UUID v) { return ProductId.of(v); }
    default UUID fromProductId(ProductId v) { return v.value(); }

    default SKU toSku(String v) { return SKU.of(v); }
    default String fromSku(SKU v) { return v.value(); }

    default ProductName toName(String v) { return ProductName.of(v); }
    default String fromName(ProductName v) { return v.value(); }

    default Stock toStock(int v) { return Stock.of(v); }
    default int fromStock(Stock v) { return v.value(); }

    default CategoryReference toCategory(String v) { return v != null ? CategoryReference.of(v) : null; }
    default String fromCategory(CategoryReference v) { return v != null ? v.categoryId() : null; }

    default ProductImage toImage(String v) { return v != null ? ProductImage.of(v) : null; }
    default String fromImage(ProductImage v) { return v != null ? v.imageUrl() : null; }

    default Money toMoney(BigDecimal amount, String currency) {
        return Money.of(amount, Currency.getInstance(currency));
    }

    default BigDecimal fromMoneyAmount(Money m) { return m.amount(); }
    default String fromMoneyCurrency(Money m) { return m.currency().getCurrencyCode(); }

    default AuditInfo toAudit(String createdBy, Instant createdAt, Instant updatedAt) {
        return new AuditInfo(createdBy != null ? createdBy : "unknown", createdAt, updatedAt);
    }
}
