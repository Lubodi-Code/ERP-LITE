package com.erp.domain;

import com.erp.domain.product.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateStock_whenValueIsZero() {
        Stock stock = new Stock(0);

        assertEquals(0, stock.value());
    }

    @Test
    void constructor_shouldCreateStock_whenValueIsPositive() {
        Stock stock = new Stock(100);

        assertEquals(100, stock.value());
    }

    @Test
    void constructor_shouldThrow_whenValueIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Stock(-1));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateStock_whenValueIsValid() {
        Stock stock = Stock.of(50);

        assertEquals(50, stock.value());
    }

    @Test
    void zero_shouldReturnStockWithValueZero() {
        Stock stock = Stock.zero();

        assertEquals(0, stock.value());
    }

    // -------------------------------------------------------------------------
    // increment
    // -------------------------------------------------------------------------

    @Test
    void increment_shouldReturnNewStockWithIncreasedValue() {
        Stock stock = Stock.of(10);

        Stock result = stock.increment(5);

        assertEquals(15, result.value());
    }

    @Test
    void increment_shouldThrow_whenQuantityIsZero() {
        Stock stock = Stock.of(10);

        assertThrows(IllegalArgumentException.class, () -> stock.increment(0));
    }

    @Test
    void increment_shouldThrow_whenQuantityIsNegative() {
        Stock stock = Stock.of(10);

        assertThrows(IllegalArgumentException.class, () -> stock.increment(-1));
    }

    // -------------------------------------------------------------------------
    // decrement
    // -------------------------------------------------------------------------

    @Test
    void decrement_shouldReturnNewStockWithDecreasedValue() {
        Stock stock = Stock.of(10);

        Stock result = stock.decrement(3);

        assertEquals(7, result.value());
    }

    @Test
    void decrement_shouldReturnZero_whenDecrementingByFullAmount() {
        Stock stock = Stock.of(5);

        Stock result = stock.decrement(5);

        assertEquals(0, result.value());
    }

    @Test
    void decrement_shouldThrow_whenQuantityIsZero() {
        Stock stock = Stock.of(10);

        assertThrows(IllegalArgumentException.class, () -> stock.decrement(0));
    }

    @Test
    void decrement_shouldThrow_whenQuantityIsNegative() {
        Stock stock = Stock.of(10);

        assertThrows(IllegalArgumentException.class, () -> stock.decrement(-1));
    }

    @Test
    void decrement_shouldThrow_whenInsufficientStock() {
        Stock stock = Stock.of(3);

        assertThrows(IllegalArgumentException.class, () -> stock.decrement(5));
    }

    // -------------------------------------------------------------------------
    // hasAvailable
    // -------------------------------------------------------------------------

    @Test
    void hasAvailable_shouldReturnTrue_whenStockMeetsRequired() {
        Stock stock = Stock.of(10);

        assertTrue(stock.hasAvailable(10));
    }

    @Test
    void hasAvailable_shouldReturnTrue_whenStockExceedsRequired() {
        Stock stock = Stock.of(10);

        assertTrue(stock.hasAvailable(5));
    }

    @Test
    void hasAvailable_shouldReturnFalse_whenStockIsInsufficient() {
        Stock stock = Stock.of(3);

        assertFalse(stock.hasAvailable(5));
    }
}
