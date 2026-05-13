package com.erp.domain;

import com.erp.domain.catalog.Catalog;
import com.erp.domain.catalog.CatalogItem;
import com.erp.domain.catalog.CatalogType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {

    private static CatalogItem activeItem(String id, String code) {
        return new CatalogItem(id, code, "Value " + code, null, 1, null);
    }

    private static CatalogItem inactiveItem(String id, String code) {
        CatalogItem item = new CatalogItem(id, code, "Value " + code, null, 2, null);
        item.turnOffStatus();
        return item;
    }

    private static Catalog createCatalog(List<CatalogItem> items) {
        return new Catalog("cat-001", "Product Categories", CatalogType.PRODUCT_CATEGORIES, "Desc", items, true);
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateCatalog_whenValidArguments() {
        Catalog catalog = createCatalog(List.of());

        assertEquals("cat-001", catalog.getId());
        assertEquals("Product Categories", catalog.getName());
        assertEquals(CatalogType.PRODUCT_CATEGORIES, catalog.getCatalogType());
        assertEquals("Desc", catalog.getDescription());
        assertTrue(catalog.isActive());
    }

    @Test
    void constructor_shouldThrow_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Catalog(null, "Name", CatalogType.PRODUCT_CATEGORIES, null, null, true));
    }

    @Test
    void constructor_shouldThrow_whenIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Catalog("  ", "Name", CatalogType.PRODUCT_CATEGORIES, null, null, true));
    }

    @Test
    void constructor_shouldThrow_whenNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Catalog("cat-001", null, CatalogType.PRODUCT_CATEGORIES, null, null, true));
    }

    @Test
    void constructor_shouldThrow_whenNameIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Catalog("cat-001", "  ", CatalogType.PRODUCT_CATEGORIES, null, null, true));
    }

    @Test
    void constructor_shouldThrow_whenCatalogTypeIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Catalog("cat-001", "Name", null, null, null, true));
    }

    @Test
    void constructor_shouldInitializeEmptyList_whenItemsIsNull() {
        Catalog catalog = new Catalog("cat-001", "Name", CatalogType.COUNTRIES, null, null, true);

        assertTrue(catalog.findAll().isEmpty());
    }

    @Test
    void constructor_shouldAllowNullDescription() {
        assertDoesNotThrow(() -> new Catalog("cat-001", "Name", CatalogType.CURRENCIES, null, List.of(), true));
    }

    // -------------------------------------------------------------------------
    // findItemByCode
    // -------------------------------------------------------------------------

    @Test
    void findItemByCode_shouldReturnItem_whenCodeExists() {
        CatalogItem item = activeItem("1", "CODE_01");
        Catalog catalog = createCatalog(List.of(item));

        Optional<CatalogItem> result = catalog.findItemByCode("CODE_01");

        assertTrue(result.isPresent());
        assertEquals("CODE_01", result.get().getCode());
    }

    @Test
    void findItemByCode_shouldReturnEmpty_whenCodeDoesNotExist() {
        Catalog catalog = createCatalog(List.of(activeItem("1", "CODE_01")));

        Optional<CatalogItem> result = catalog.findItemByCode("MISSING");

        assertFalse(result.isPresent());
    }

    // -------------------------------------------------------------------------
    // containsItemByCode
    // -------------------------------------------------------------------------

    @Test
    void containsItemByCode_shouldReturnTrue_whenCodeExists() {
        Catalog catalog = createCatalog(List.of(activeItem("1", "CODE_01")));

        assertTrue(catalog.containsItemByCode("CODE_01"));
    }

    @Test
    void containsItemByCode_shouldReturnFalse_whenCodeDoesNotExist() {
        Catalog catalog = createCatalog(List.of(activeItem("1", "CODE_01")));

        assertFalse(catalog.containsItemByCode("MISSING"));
    }

    // -------------------------------------------------------------------------
    // findActiveItems
    // -------------------------------------------------------------------------

    @Test
    void findActiveItems_shouldReturnOnlyActiveItems() {
        CatalogItem active = activeItem("1", "ACTIVE");
        CatalogItem inactive = inactiveItem("2", "INACTIVE");
        Catalog catalog = createCatalog(List.of(active, inactive));

        List<CatalogItem> result = catalog.findActiveItems();

        assertEquals(1, result.size());
        assertEquals("ACTIVE", result.get(0).getCode());
    }

    @Test
    void findActiveItems_shouldReturnEmptyList_whenNoActiveItems() {
        Catalog catalog = createCatalog(List.of(inactiveItem("1", "INACTIVE")));

        List<CatalogItem> result = catalog.findActiveItems();

        assertTrue(result.isEmpty());
    }

    // -------------------------------------------------------------------------
    // findAll
    // -------------------------------------------------------------------------

    @Test
    void findAll_shouldReturnAllItems_includingInactive() {
        CatalogItem active = activeItem("1", "ACTIVE");
        CatalogItem inactive = inactiveItem("2", "INACTIVE");
        Catalog catalog = createCatalog(List.of(active, inactive));

        List<CatalogItem> result = catalog.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findAll_shouldReturnUnmodifiableList() {
        Catalog catalog = createCatalog(List.of(activeItem("1", "CODE_01")));

        List<CatalogItem> result = catalog.findAll();

        assertThrows(UnsupportedOperationException.class, () -> result.add(activeItem("2", "CODE_02")));
    }
}
