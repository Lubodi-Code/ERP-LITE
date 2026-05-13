package com.erp.domain;

import com.erp.domain.catalog.CatalogType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTypeTest {

    // -------------------------------------------------------------------------
    // fromCode
    // -------------------------------------------------------------------------

    @Test
    void fromCode_shouldReturnProductCategories_whenValidCode() {
        CatalogType type = CatalogType.fromCode("PRODUCT_CATEGORIES");

        assertEquals(CatalogType.PRODUCT_CATEGORIES, type);
    }

    @Test
    void fromCode_shouldReturnOrderStatuses_whenValidCode() {
        assertEquals(CatalogType.ORDER_STATUSES, CatalogType.fromCode("ORDER_STATUSES"));
    }

    @Test
    void fromCode_shouldReturnPaymentMethods_whenValidCode() {
        assertEquals(CatalogType.PAYMENT_METHODS, CatalogType.fromCode("PAYMENT_METHODS"));
    }

    @Test
    void fromCode_shouldReturnShippingMethods_whenValidCode() {
        assertEquals(CatalogType.SHIPPING_METHODS, CatalogType.fromCode("SHIPPING_METHODS"));
    }

    @Test
    void fromCode_shouldReturnCountries_whenValidCode() {
        assertEquals(CatalogType.COUNTRIES, CatalogType.fromCode("COUNTRIES"));
    }

    @Test
    void fromCode_shouldReturnCurrencies_whenValidCode() {
        assertEquals(CatalogType.CURRENCIES, CatalogType.fromCode("CURRENCIES"));
    }

    @Test
    void fromCode_shouldThrow_whenCodeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CatalogType.fromCode(null));
    }

    @Test
    void fromCode_shouldThrow_whenCodeIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> CatalogType.fromCode("  "));
    }

    @Test
    void fromCode_shouldThrow_whenCodeIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> CatalogType.fromCode("UNKNOWN_TYPE"));
    }

    @Test
    void fromCode_shouldThrow_whenCodeIsLowercase() {
        assertThrows(IllegalArgumentException.class, () -> CatalogType.fromCode("product_categories"));
    }

    // -------------------------------------------------------------------------
    // isValid
    // -------------------------------------------------------------------------

    @Test
    void isValid_shouldReturnTrue_whenValidCode() {
        assertTrue(CatalogType.isValid("PRODUCT_CATEGORIES"));
    }

    @Test
    void isValid_shouldReturnFalse_whenCodeIsNull() {
        assertFalse(CatalogType.isValid(null));
    }

    @Test
    void isValid_shouldReturnFalse_whenCodeIsBlank() {
        assertFalse(CatalogType.isValid("  "));
    }

    @Test
    void isValid_shouldReturnFalse_whenCodeIsInvalid() {
        assertFalse(CatalogType.isValid("NOT_A_TYPE"));
    }

    // -------------------------------------------------------------------------
    // getCode / getDisplayName
    // -------------------------------------------------------------------------

    @Test
    void getCode_shouldReturnCodeString() {
        assertEquals("PRODUCT_CATEGORIES", CatalogType.PRODUCT_CATEGORIES.getCode());
        assertEquals("ORDER_STATUSES", CatalogType.ORDER_STATUSES.getCode());
    }

    @Test
    void getDisplayName_shouldReturnHumanReadableName() {
        assertEquals("Product Categories", CatalogType.PRODUCT_CATEGORIES.getDisplayName());
        assertEquals("Order Statuses", CatalogType.ORDER_STATUSES.getDisplayName());
        assertEquals("Payment Methods", CatalogType.PAYMENT_METHODS.getDisplayName());
        assertEquals("Shipping Methods", CatalogType.SHIPPING_METHODS.getDisplayName());
        assertEquals("Countries", CatalogType.COUNTRIES.getDisplayName());
        assertEquals("Currencies", CatalogType.CURRENCIES.getDisplayName());
    }
}
