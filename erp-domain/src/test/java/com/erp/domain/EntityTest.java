package com.erp.domain;

import com.erp.domain.common.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private static class StringEntity extends Entity<String> {
        StringEntity(String id) { super(id); }
    }

    private static class OtherEntity extends Entity<String> {
        OtherEntity(String id) { super(id); }
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateEntity_whenIdIsNotNull() {
        StringEntity entity = new StringEntity("abc");

        assertEquals("abc", entity.getId());
    }

    @Test
    void constructor_shouldThrow_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new StringEntity(null));
    }

    // -------------------------------------------------------------------------
    // equals
    // -------------------------------------------------------------------------

    @Test
    void equals_shouldReturnTrue_whenSameInstance() {
        StringEntity entity = new StringEntity("1");

        assertEquals(entity, entity);
    }

    @Test
    void equals_shouldReturnTrue_whenSameClassAndSameId() {
        StringEntity a = new StringEntity("1");
        StringEntity b = new StringEntity("1");

        assertEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenSameClassButDifferentId() {
        StringEntity a = new StringEntity("1");
        StringEntity b = new StringEntity("2");

        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentClass() {
        StringEntity a = new StringEntity("1");
        OtherEntity b = new OtherEntity("1");

        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedToNull() {
        StringEntity entity = new StringEntity("1");

        assertNotEquals(null, entity);
    }

    // -------------------------------------------------------------------------
    // hashCode
    // -------------------------------------------------------------------------

    @Test
    void hashCode_shouldBeEqual_whenSameId() {
        StringEntity a = new StringEntity("1");
        StringEntity b = new StringEntity("1");

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hashCode_shouldDiffer_whenDifferentId() {
        StringEntity a = new StringEntity("1");
        StringEntity b = new StringEntity("2");

        assertNotEquals(a.hashCode(), b.hashCode());
    }
}
