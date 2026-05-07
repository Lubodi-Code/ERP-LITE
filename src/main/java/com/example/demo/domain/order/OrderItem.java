package com.example.demo.domain.order;

import com.example.demo.domain.common.Entity;
import com.example.demo.domain.product.Product;
import com.example.demo.domain.shared.Money;
import com.example.demo.domain.shared.Quantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class OrderItem extends Entity<OrderItemId> {

    private OrderItemId id;
    private com.example.demo.domain.product.ProductId productReference;
    private String productName;
    private Quantity quantity;
    private Money unitPrice;
    private Money subtotal;

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

    @Override
    public OrderItemId getId() {
        return id;
    }
}
