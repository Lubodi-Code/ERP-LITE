package com.erp.domain.order;

import com.erp.domain.common.Entity;
import com.erp.domain.product.Product;
import com.erp.domain.shared.Money;
import com.erp.domain.shared.Quantity;
import lombok.Getter;

@Getter
public class OrderItem extends Entity<OrderItemId> {

    private final com.erp.domain.product.ProductId productReference;
    private final String productName;
    private final Quantity quantity;
    private final Money unitPrice;
    private final Money subtotal;

    private OrderItem(OrderItemId id, com.erp.domain.product.ProductId productReference,
                      String productName, Quantity quantity, Money unitPrice, Money subtotal) {
        super(id);
        this.productReference = productReference;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public static OrderItem rehydrate(OrderItemId id, com.erp.domain.product.ProductId productReference,
                                      String productName, Quantity quantity,
                                      Money unitPrice, Money subtotal) {
        return new OrderItem(id, productReference, productName, quantity, unitPrice, subtotal);
    }

    public static OrderItem from(Product product, Quantity quantity) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        if (quantity == null) throw new IllegalArgumentException("Quantity must not be null");
        if (!product.isActive()) throw new IllegalArgumentException("Cannot order an inactive product");
        if (!product.hasAvailableStock(quantity.value()))
            throw new IllegalArgumentException(
                    "Insufficient stock for product " + product.getSku().value());

        OrderItemId id = OrderItemId.generate();
        String snapshotName = product.getName().value();
        Money snapshotPrice = product.getPrice();
        Money subtotal = snapshotPrice.multiply(quantity);
        return new OrderItem(id, product.getId(), snapshotName, quantity, snapshotPrice, subtotal);
    }

    public Money calculateSubtotal() {
        return this.unitPrice.multiply(this.quantity);
    }
}
