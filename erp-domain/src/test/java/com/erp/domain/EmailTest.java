package com.erp.domain;

import com.erp.domain.shared.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateEmail_whenValidFormat() {
        Email email = new Email("user@example.com");

        assertEquals("user@example.com", email.value());
    }

    @Test
    void constructor_shouldCreateEmail_whenComplexValidFormat() {
        assertDoesNotThrow(() -> new Email("user.name+tag@sub.domain.org"));
    }

    @Test
    void constructor_shouldThrow_whenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    void constructor_shouldThrow_whenValueIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Email("  "));
    }

    @Test
    void constructor_shouldThrow_whenMissingAtSign() {
        assertThrows(IllegalArgumentException.class, () -> new Email("userexample.com"));
    }

    @Test
    void constructor_shouldThrow_whenMissingDomain() {
        assertThrows(IllegalArgumentException.class, () -> new Email("user@"));
    }

    @Test
    void constructor_shouldThrow_whenMissingTld() {
        assertThrows(IllegalArgumentException.class, () -> new Email("user@example"));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateEmail_whenValidFormat() {
        Email email = Email.of("admin@domain.io");

        assertEquals("admin@domain.io", email.value());
    }

    @Test
    void of_shouldThrow_whenInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> Email.of("not-an-email"));
    }
}
