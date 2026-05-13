package com.erp.domain;

import com.erp.domain.shared.AuditInfo;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class AuditInfoTest {

    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateAuditInfo_whenValidArguments() {
        AuditInfo audit = new AuditInfo("admin", NOW, NOW);

        assertEquals("admin", audit.createdBy());
        assertEquals(NOW, audit.createdAt());
        assertEquals(NOW, audit.updatedAt());
    }

    @Test
    void constructor_shouldThrow_whenCreatedByIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new AuditInfo(null, NOW, NOW));
    }

    @Test
    void constructor_shouldThrow_whenCreatedByIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new AuditInfo("  ", NOW, NOW));
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new AuditInfo("admin", null, NOW));
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new AuditInfo("admin", NOW, null));
    }

    // -------------------------------------------------------------------------
    // create
    // -------------------------------------------------------------------------

    @Test
    void create_shouldSetCreatedAtAndUpdatedAtToSameTimestamp() {
        AuditInfo audit = AuditInfo.create("admin", NOW);

        assertEquals("admin", audit.createdBy());
        assertEquals(NOW, audit.createdAt());
        assertEquals(NOW, audit.updatedAt());
    }

    // -------------------------------------------------------------------------
    // updateTimestamp
    // -------------------------------------------------------------------------

    @Test
    void updateTimestamp_shouldReturnNewInstanceWithUpdatedAt() {
        AuditInfo original = AuditInfo.create("admin", NOW);

        AuditInfo updated = original.updateTimestamp();

        assertEquals("admin", updated.createdBy());
        assertEquals(NOW, updated.createdAt());
        assertNotNull(updated.updatedAt());
        assertNotSame(original, updated);
    }

    @Test
    void updateTimestamp_shouldNotChangeCreatedAt() {
        AuditInfo original = AuditInfo.create("admin", NOW);

        AuditInfo updated = original.updateTimestamp();

        assertEquals(original.createdAt(), updated.createdAt());
    }
}
