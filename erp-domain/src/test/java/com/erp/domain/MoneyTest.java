package com.erp.domain;

import com.erp.domain.shared.Money;
import com.erp.domain.shared.Quantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    // -------------------------------------------------------------------------
    // Constructor / compact constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateMoney_whenValidArguments() {
        Money money = new Money(BigDecimal.valueOf(10.50), USD);

        assertEquals(0, BigDecimal.valueOf(10.50).compareTo(money.amount()));
        assertEquals(USD, money.currency());
    }

    @Test
    void constructor_shouldThrow_whenAmountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Money(null, USD));
    }

    @Test
    void constructor_shouldThrow_whenCurrencyIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.ONE, null));
    }

    @Test
    void constructor_shouldThrow_whenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(BigDecimal.valueOf(-0.01), USD));
    }

    @Test
    void constructor_shouldAllowZeroAmount() {
        assertDoesNotThrow(() -> new Money(BigDecimal.ZERO, USD));
    }

    @Test
    void constructor_shouldScaleAmountToTwoDecimalPlaces() {
        Money money = new Money(BigDecimal.valueOf(10.999), USD);

        assertEquals(2, money.amount().scale());
        assertEquals(0, BigDecimal.valueOf(11.00).compareTo(money.amount()));
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    @Test
    void of_bigDecimal_shouldCreateMoney() {
        Money money = Money.of(BigDecimal.valueOf(5.00), USD);

        assertEquals(0, BigDecimal.valueOf(5.00).compareTo(money.amount()));
        assertEquals(USD, money.currency());
    }

    @Test
    void of_double_shouldCreateMoney() {
        Money money = Money.of(9.99, USD);

        assertEquals(0, BigDecimal.valueOf(9.99).compareTo(money.amount()));
        assertEquals(USD, money.currency());
    }

    // -------------------------------------------------------------------------
    // add
    // -------------------------------------------------------------------------

    @Test
    void add_shouldReturnSum_whenSameCurrency() {
        Money a = Money.of(10.00, USD);
        Money b = Money.of(5.00, USD);

        Money result = a.add(b);

        assertEquals(0, BigDecimal.valueOf(15.00).compareTo(result.amount()));
        assertEquals(USD, result.currency());
    }

    @Test
    void add_shouldThrow_whenDifferentCurrencies() {
        Money usd = Money.of(10.00, USD);
        Money eur = Money.of(5.00, EUR);

        assertThrows(IllegalArgumentException.class, () -> usd.add(eur));
    }

    // -------------------------------------------------------------------------
    // subtract
    // -------------------------------------------------------------------------

    @Test
    void subtract_shouldReturnDifference_whenSameCurrencyAndResultPositive() {
        Money a = Money.of(10.00, USD);
        Money b = Money.of(3.00, USD);

        Money result = a.subtract(b);

        assertEquals(0, BigDecimal.valueOf(7.00).compareTo(result.amount()));
    }

    @Test
    void subtract_shouldReturnZero_whenAmountsAreEqual() {
        Money a = Money.of(5.00, USD);

        Money result = a.subtract(a);

        assertEquals(0, BigDecimal.ZERO.compareTo(result.amount()));
    }

    @Test
    void subtract_shouldThrow_whenResultWouldBeNegative() {
        Money a = Money.of(3.00, USD);
        Money b = Money.of(10.00, USD);

        assertThrows(IllegalArgumentException.class, () -> a.subtract(b));
    }

    @Test
    void subtract_shouldThrow_whenDifferentCurrencies() {
        Money usd = Money.of(10.00, USD);
        Money eur = Money.of(5.00, EUR);

        assertThrows(IllegalArgumentException.class, () -> usd.subtract(eur));
    }

    // -------------------------------------------------------------------------
    // multiply(int)
    // -------------------------------------------------------------------------

    @Test
    void multiply_int_shouldReturnProduct_whenMultiplierPositive() {
        Money money = Money.of(10.00, USD);

        Money result = money.multiply(3);

        assertEquals(0, BigDecimal.valueOf(30.00).compareTo(result.amount()));
        assertEquals(USD, result.currency());
    }

    @Test
    void multiply_int_shouldReturnZero_whenMultiplierIsZero() {
        Money money = Money.of(10.00, USD);

        Money result = money.multiply(0);

        assertEquals(0, BigDecimal.ZERO.compareTo(result.amount()));
    }

    @Test
    void multiply_int_shouldThrow_whenMultiplierIsNegative() {
        Money money = Money.of(10.00, USD);

        assertThrows(IllegalArgumentException.class, () -> money.multiply(-1));
    }

    // -------------------------------------------------------------------------
    // multiply(Quantity)
    // -------------------------------------------------------------------------

    @Test
    void multiply_quantity_shouldReturnProduct() {
        Money money = Money.of(10.00, USD);
        Quantity quantity = Quantity.of(4);

        Money result = money.multiply(quantity);

        assertEquals(0, BigDecimal.valueOf(40.00).compareTo(result.amount()));
    }
}
