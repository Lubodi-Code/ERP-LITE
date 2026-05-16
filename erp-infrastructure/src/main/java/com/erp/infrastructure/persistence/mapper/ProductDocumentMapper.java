package com.erp.infrastructure.persistence.mapper;

import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.Product;
import com.erp.domain.product.ProductId;
import com.erp.domain.product.ProductImage;
import com.erp.domain.product.ProductName;
import com.erp.domain.product.SKU;
import com.erp.domain.product.Stock;
import com.erp.domain.shared.AuditInfo;
import com.erp.domain.shared.Money;
import com.erp.infrastructure.persistence.mongo.document.ProductInCatalogDocument;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductDocumentMapper {

    default ProductInCatalogDocument toDocument(Product product) {
        return ProductInCatalogDocument.builder()
                .id(product.getId().value().toString())
                .active(product.isActive())
                .categoryId(product.getCategory().categoryId())
                .categoryName(null)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .currency(product.getPrice().currency().getCurrencyCode())
                .description(product.getDescription())
                .imageUrl(product.getImage() != null ? product.getImage().imageUrl() : null)
                .name(product.getName().value())
                .price(product.getPrice().amount())
                .sku(product.getSku().value())
                .specifications(null)
                .stock(product.getStock().value())
                .tags(List.of())
                .build();
    }

    default Product toDomain(ProductInCatalogDocument doc) {
        return Product.rehydrate(
                ProductId.of(UUID.fromString(doc.getId())),
                SKU.of(doc.getSku()),
                ProductName.of(doc.getName()),
                doc.getDescription(),
                Money.of(doc.getPrice(), Currency.getInstance(doc.getCurrency())),
                Stock.of(doc.getStock()),
                CategoryReference.of(doc.getCategoryId()),
                doc.getImageUrl() != null ? ProductImage.of(doc.getImageUrl()) : null,
                doc.isActive(),
                new AuditInfo("unknown", doc.getCreatedAt(), doc.getUpdatedAt())
        );
    }
}
