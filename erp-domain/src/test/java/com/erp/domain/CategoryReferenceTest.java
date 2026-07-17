package com.erp.domain;

import com.erp.domain.product.CategoryReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryReferenceTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateCategoryReference_whenValidId() {
        CategoryReference ref = new CategoryReference("cat-electronics");

        assertEquals("cat-electronics", ref.categoryId());
    }

    @Test
    void constructor_shouldThrow_whenCategoryIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new CategoryReference(null));
    }

    @Test
    void constructor_shouldThrow_whenCategoryIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new CategoryReference("  "));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateCategoryReference_whenValidId() {
        CategoryReference ref = CategoryReference.of("cat-furniture");

        assertEquals("cat-furniture", ref.categoryId());
    }

    @Test
    void of_shouldThrow_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CategoryReference.of(null));
    }
}
