package com.erp.domain;

import com.erp.domain.product.ProductImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductImageTest {

    private static final String VALID_URL = "https://example.com/images/product.jpg";

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateProductImage_whenHttpsUrl() {
        ProductImage image = new ProductImage(VALID_URL);

        assertEquals(VALID_URL, image.imageUrl());
    }

    @Test
    void constructor_shouldCreateProductImage_whenHttpUrl() {
        assertDoesNotThrow(() -> new ProductImage("http://example.com/image.png"));
    }

    @Test
    void constructor_shouldThrow_whenImageUrlIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ProductImage(null));
    }

    @Test
    void constructor_shouldThrow_whenImageUrlIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ProductImage("  "));
    }

    @Test
    void constructor_shouldThrow_whenNotAUrl() {
        assertThrows(IllegalArgumentException.class, () -> new ProductImage("not-a-url"));
    }

    @Test
    void constructor_shouldThrow_whenMissingProtocol() {
        assertThrows(IllegalArgumentException.class, () -> new ProductImage("example.com/image.jpg"));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateProductImage_whenValidUrl() {
        ProductImage image = ProductImage.of(VALID_URL);

        assertEquals(VALID_URL, image.imageUrl());
    }

    // -------------------------------------------------------------------------
    // getFullUrl
    // -------------------------------------------------------------------------

    @Test
    void getFullUrl_shouldReturnImageUrl() {
        ProductImage image = new ProductImage(VALID_URL);

        assertEquals(VALID_URL, image.getFullUrl());
    }

    // -------------------------------------------------------------------------
    // getFileName
    // -------------------------------------------------------------------------

    @Test
    void getFileName_shouldReturnFilenameFromUrl() {
        ProductImage image = new ProductImage(VALID_URL);

        assertEquals("product.jpg", image.getFileName());
    }

    @Test
    void getFileName_shouldReturnFilename_whenUrlHasMultipleSegments() {
        ProductImage image = new ProductImage("https://cdn.example.com/a/b/c/photo.png");

        assertEquals("photo.png", image.getFileName());
    }
}
