package com.erp.domain;

import com.erp.domain.common.DomainEvent;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.product.*;
import com.erp.domain.product.events.ProductCreated;
import com.erp.domain.product.events.ProductDeactivated;
import com.erp.domain.product.events.ProductUpdated;
import com.erp.domain.product.events.StockChanged;
import com.erp.domain.shared.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private static final Currency USD = Currency.getInstance("USD");

    private static ProductRoot createValidProduct() {
        return ProductRoot.create(
                SKU.of("LAPTOP-001"),
                ProductName.of("Test Laptop"),
                "A test description",
                Money.of(100.00, USD),
                Stock.of(10),
                CategoryReference.of("cat-electronics"),
                ProductImage.of("https://example.com/laptop.jpg"),
                "admin"
        );
    }

    // -------------------------------------------------------------------------
    // create
    // -------------------------------------------------------------------------

    @Test
    void create_shouldCreateActiveProduct_whenValidArguments() {
        ProductRoot product = createValidProduct();

        assertNotNull(product.getId());
        assertEquals("LAPTOP-001", product.getSku().value());
        assertEquals("Test Laptop", product.getName().value());
        assertEquals("A test description", product.getDescription());
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(product.getPrice().amount()));
        assertEquals(10, product.getStock().value());
        assertTrue(product.isActive());
        assertNotNull(product.getAuditInfo());
    }

    @Test
    void create_shouldRegisterProductCreatedEvent() {
        ProductRoot product = createValidProduct();

        List<DomainEvent> events = product.pullDomainEvents();

        assertEquals(1, events.size());
        assertInstanceOf(ProductCreated.class, events.get(0));
    }

    @Test
    void create_shouldThrow_whenPriceIsZero() {
        assertThrows(IllegalArgumentException.class, () -> ProductRoot.create(
                SKU.of("LAPTOP-001"),
                ProductName.of("Test Laptop"),
                "desc",
                Money.of(0.00, USD),
                Stock.of(10),
                CategoryReference.of("cat-electronics"),
                ProductImage.of("https://example.com/img.jpg"),
                "admin"
        ));
    }

    // -------------------------------------------------------------------------
    // update
    // -------------------------------------------------------------------------

    @Test
    void update_shouldUpdateProductFields_whenValidArguments() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.update(
                ProductName.of("Updated Laptop"),
                "New description",
                Money.of(200.00, USD),
                CategoryReference.of("cat-computing"),
                ProductImage.of("https://example.com/new.jpg")
        );

        assertEquals("Updated Laptop", product.getName().value());
        assertEquals("New description", product.getDescription());
        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(product.getPrice().amount()));
    }

    @Test
    void update_shouldRegisterProductUpdatedEvent() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.update(
                ProductName.of("Updated Laptop"),
                "desc",
                Money.of(50.00, USD),
                CategoryReference.of("cat-electronics"),
                ProductImage.of("https://example.com/img.jpg")
        );

        List<DomainEvent> events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(ProductUpdated.class, events.get(0));
    }

    @Test
    void update_shouldThrow_whenNewPriceIsZero() {
        ProductRoot product = createValidProduct();

        assertThrows(IllegalArgumentException.class, () -> product.update(
                ProductName.of("Updated"),
                "desc",
                Money.of(0.00, USD),
                CategoryReference.of("cat-electronics"),
                ProductImage.of("https://example.com/img.jpg")
        ));
    }

    // -------------------------------------------------------------------------
    // incrementStock
    // -------------------------------------------------------------------------

    @Test
    void incrementStock_shouldIncreaseStock_whenValidQuantity() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.incrementStock(5, "restock");

        assertEquals(15, product.getStock().value());
    }

    @Test
    void incrementStock_shouldRegisterStockChangedEvent() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.incrementStock(5, "restock");

        List<DomainEvent> events = product.pullDomainEvents();
        assertEquals(1, events.size());
        StockChanged event = (StockChanged) events.get(0);
        assertEquals(10, event.oldStock());
        assertEquals(15, event.newStock());
        assertEquals("restock", event.reason());
    }

    // -------------------------------------------------------------------------
    // decrementStock
    // -------------------------------------------------------------------------

    @Test
    void decrementStock_shouldDecreaseStock_whenValidQuantity() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.decrementStock(3, "sold");

        assertEquals(7, product.getStock().value());
    }

    @Test
    void decrementStock_shouldRegisterStockChangedEvent() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.decrementStock(3, "sold");

        List<DomainEvent> events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(StockChanged.class, events.get(0));
    }

    @Test
    void decrementStock_shouldThrow_whenInsufficientStock() {
        ProductRoot product = createValidProduct();

        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(100, "sold"));
    }

    // -------------------------------------------------------------------------
    // changePrice
    // -------------------------------------------------------------------------

    @Test
    void changePrice_shouldUpdatePrice_whenValidPrice() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.changePrice(Money.of(150.00, USD));

        assertEquals(0, BigDecimal.valueOf(150.00).compareTo(product.getPrice().amount()));
    }

    @Test
    void changePrice_shouldRegisterProductUpdatedEvent() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.changePrice(Money.of(150.00, USD));

        List<DomainEvent> events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(ProductUpdated.class, events.get(0));
    }

    @Test
    void changePrice_shouldThrow_whenPriceIsZero() {
        ProductRoot product = createValidProduct();

        assertThrows(IllegalArgumentException.class, () -> product.changePrice(Money.of(0.00, USD)));
    }

    // -------------------------------------------------------------------------
    // deactivate
    // -------------------------------------------------------------------------

    @Test
    void deactivate_shouldSetActiveToFalse_whenProductIsActive() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.deactivate();

        assertFalse(product.isActive());
    }

    @Test
    void deactivate_shouldRegisterProductDeactivatedEvent() {
        ProductRoot product = createValidProduct();
        product.pullDomainEvents();

        product.deactivate();

        List<DomainEvent> events = product.pullDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(ProductDeactivated.class, events.get(0));
    }

    @Test
    void deactivate_shouldThrow_whenProductIsAlreadyInactive() {
        ProductRoot product = createValidProduct();
        product.deactivate();

        assertThrows(IllegalStateException.class, product::deactivate);
    }

    // -------------------------------------------------------------------------
    // activate
    // -------------------------------------------------------------------------

    @Test
    void activate_shouldSetActiveToTrue_whenProductIsInactive() {
        ProductRoot product = createValidProduct();
        product.deactivate();

        product.activate();

        assertTrue(product.isActive());
    }

    @Test
    void activate_shouldThrow_whenProductIsAlreadyActive() {
        ProductRoot product = createValidProduct();

        assertThrows(IllegalStateException.class, product::activate);
    }

    // -------------------------------------------------------------------------
    // hasAvailableStock
    // -------------------------------------------------------------------------

    @Test
    void hasAvailableStock_shouldReturnTrue_whenStockSufficient() {
        ProductRoot product = createValidProduct();

        assertTrue(product.hasAvailableStock(5));
    }

    @Test
    void hasAvailableStock_shouldReturnFalse_whenStockInsufficient() {
        ProductRoot product = createValidProduct();

        assertFalse(product.hasAvailableStock(100));
    }
}
