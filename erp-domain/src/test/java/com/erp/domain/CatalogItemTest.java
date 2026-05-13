package com.erp.domain;

import com.erp.domain.catalog.CatalogItem;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CatalogItemTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateActiveItem_whenValidArguments() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", "Desc", 1, null);

        assertEquals("1", item.getId());
        assertEquals("CODE_01", item.getCode());
        assertEquals("Value", item.getValue());
        assertTrue(item.isActive());
    }

    @Test
    void constructor_shouldThrow_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem(null, "CODE_01", "Value", "Desc", 1, null));
    }

    @Test
    void constructor_shouldThrow_whenCodeIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem("1", null, "Value", "Desc", 1, null));
    }

    @Test
    void constructor_shouldThrow_whenCodeIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem("1", "  ", "Value", "Desc", 1, null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem("1", "CODE_01", null, "Desc", 1, null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem("1", "CODE_01", "  ", "Desc", 1, null));
    }

    @Test
    void constructor_shouldAllowNullDescriptionAndDisplayOrder() {
        assertDoesNotThrow(() -> new CatalogItem("1", "CODE_01", "Value", null, null, null));
    }

    // -------------------------------------------------------------------------
    // turnOffStatus
    // -------------------------------------------------------------------------

    @Test
    void turnOffStatus_shouldSetActiveToFalse() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1, null);

        assertTrue(item.isActive());
        item.turnOffStatus();
        assertFalse(item.isActive());
    }

    // -------------------------------------------------------------------------
    // getMetadata
    // -------------------------------------------------------------------------

    @Test
    void getMetadata_shouldReturnValue_whenKeyExists() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertEquals("red", item.getMetadata("color"));
    }

    @Test
    void getMetadata_shouldReturnNull_whenKeyDoesNotExist() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertNull(item.getMetadata("size"));
    }

    @Test
    void getMetadata_shouldThrow_whenKeyIsNull() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertThrows(IllegalArgumentException.class, () -> item.getMetadata(null));
    }

    @Test
    void getMetadata_shouldThrow_whenKeyIsBlank() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertThrows(IllegalArgumentException.class, () -> item.getMetadata("  "));
    }

    @Test
    void getMetadata_shouldThrow_whenMetadataMapIsNull() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1, null);

        assertThrows(IllegalStateException.class, () -> item.getMetadata("color"));
    }

    // -------------------------------------------------------------------------
    // hasMetadata
    // -------------------------------------------------------------------------

    @Test
    void hasMetadata_shouldReturnTrue_whenKeyExists() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertTrue(item.hasMetadata("color"));
    }

    @Test
    void hasMetadata_shouldReturnFalse_whenKeyDoesNotExist() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertFalse(item.hasMetadata("size"));
    }

    @Test
    void hasMetadata_shouldReturnFalse_whenMetadataMapIsNull() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1, null);

        assertFalse(item.hasMetadata("color"));
    }

    @Test
    void hasMetadata_shouldThrow_whenKeyIsNull() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertThrows(IllegalArgumentException.class, () -> item.hasMetadata(null));
    }

    @Test
    void hasMetadata_shouldThrow_whenKeyIsBlank() {
        CatalogItem item = new CatalogItem("1", "CODE_01", "Value", null, 1,
                Map.of("color", "red"));

        assertThrows(IllegalArgumentException.class, () -> item.hasMetadata("  "));
    }
}
