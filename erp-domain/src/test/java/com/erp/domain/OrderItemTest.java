package com.erp.domain;

import com.erp.domain.order.OrderItem;
import com.erp.domain.product.*;
import com.erp.domain.shared.Money;
import com.erp.domain.shared.Quantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private static final Currency USD = Currency.getInstance("USD");

    private static Product createActiveProduct(int stock) {
        return Product.create(
                SKU.of("LAPTOP-001"),
                ProductName.of("Test Laptop"),
                "Description",
                Money.of(50.00, USD),
                Stock.of(stock),
                CategoryReference.of("cat-electronics"),
                ProductImage.of("https://example.com/laptop.jpg"),
                "admin"
        );
    }

    // -------------------------------------------------------------------------
    // from
    // -------------------------------------------------------------------------

    @Test
    void from_shouldCreateOrderItem_whenValidProductAndQuantity() {
        Product product = createActiveProduct(10);
        Quantity quantity = Quantity.of(2);

        OrderItem item = OrderItem.from(product, quantity);

        assertNotNull(item.getId());
        assertEquals(product.getId(), item.getProductReference());
        assertEquals("Test Laptop", item.getProductName());
        assertEquals(2, item.getQuantity().value());
        assertEquals(0, BigDecimal.valueOf(50.00).compareTo(item.getUnitPrice().amount()));
    }

    @Test
    void from_shouldCalculateSubtotalAsQuantityTimesUnitPrice() {
        Product product = createActiveProduct(10);
        Quantity quantity = Quantity.of(3);

        OrderItem item = OrderItem.from(product, quantity);

        assertEquals(0, BigDecimal.valueOf(150.00).compareTo(item.getSubtotal().amount()));
    }

    @Test
    void from_shouldThrow_whenProductIsNull() {
        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(null, Quantity.of(1)));
    }

    @Test
    void from_shouldThrow_whenQuantityIsNull() {
        Product product = createActiveProduct(10);

        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(product, null));
    }

    @Test
    void from_shouldThrow_whenProductIsInactive() {
        Product product = createActiveProduct(10);
        product.deactivate();

        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(product, Quantity.of(1)));
    }

    @Test
    void from_shouldThrow_whenInsufficientStock() {
        Product product = createActiveProduct(2);

        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(product, Quantity.of(5)));
    }

    // -------------------------------------------------------------------------
    // calculateSubtotal
    // -------------------------------------------------------------------------

    @Test
    void calculateSubtotal_shouldReturnQuantityTimesUnitPrice() {
        Product product = createActiveProduct(10);
        OrderItem item = OrderItem.from(product, Quantity.of(4));

        Money subtotal = item.calculateSubtotal();

        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(subtotal.amount()));
    }
}
