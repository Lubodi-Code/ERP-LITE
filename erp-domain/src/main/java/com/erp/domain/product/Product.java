package com.erp.domain.product;

import com.erp.domain.common.AggregateRoot;
import com.erp.domain.product.events.ProductCreated;
import com.erp.domain.product.events.ProductDeactivated;
import com.erp.domain.product.events.ProductUpdated;
import com.erp.domain.product.events.StockChanged;
import com.erp.domain.shared.AuditInfo;
import com.erp.domain.shared.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Product extends AggregateRoot<ProductId> {

    private ProductId id;
    private SKU sku;
    private ProductName name;
    private String description;
    private Money price;
    private Stock stock;
    private CategoryReference category;
    private ProductImage image;
    private boolean active;
    private AuditInfo auditInfo;

    Product(ProductId id, SKU sku, ProductName name, String description,
            Money price, Stock stock, CategoryReference category,
            ProductImage image, boolean active, AuditInfo auditInfo) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.image = image;
        this.active = active;
        this.auditInfo = auditInfo;
    }

    public static Product create(SKU sku, ProductName name, String description,
                                 Money price, Stock stock, CategoryReference category,
                                 ProductImage image, String createdBy) {
        if (price.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Product price must be greater than 0");

        ProductId id = ProductId.generate();
        AuditInfo audit = AuditInfo.create(createdBy, Instant.now());
        Product product = new Product(id, sku, name, description, price, stock, category, image, true, audit);
        product.registerEvent(new ProductCreated(id, sku, name, price, audit.createdAt()));
        return product;
    }

    public void update(ProductName name, String description, Money newPrice,
                       CategoryReference category, ProductImage image) {
        if (newPrice.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Product price must be greater than 0");
        this.name = name;
        this.description = description;
        this.price = newPrice;
        this.category = category;
        this.image = image;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(this.id, Instant.now()));
    }

    public void incrementStock(int quantity, String reason) {
        int oldValue = this.stock.value();
        this.stock = this.stock.increment(quantity);
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new StockChanged(this.id, oldValue, this.stock.value(), reason, Instant.now()));
    }

    public void decrementStock(int quantity, String reason) {
        int oldValue = this.stock.value();
        this.stock = this.stock.decrement(quantity);
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new StockChanged(this.id, oldValue, this.stock.value(), reason, Instant.now()));
    }

    public void changePrice(Money newPrice) {
        if (newPrice.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Product price must be greater than 0");
        this.price = newPrice;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(this.id, Instant.now()));
    }

    public void deactivate() {
        if (!this.active) throw new IllegalStateException("Product is already inactive");
        this.active = false;
        this.auditInfo = auditInfo.updateTimestamp();
        registerEvent(new ProductDeactivated(this.id, Instant.now()));
    }

    public void activate() {
        if (this.active) throw new IllegalStateException("Product is already active");
        this.active = true;
        this.auditInfo = auditInfo.updateTimestamp();
    }

    public boolean hasAvailableStock(int requiredQuantity) {
        return this.stock.hasAvailable(requiredQuantity);
    }
}
