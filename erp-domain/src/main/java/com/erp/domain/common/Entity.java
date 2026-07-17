package com.erp.domain.common;

import java.util.Objects;

public abstract class Entity<ID> {

    private final ID id;

    protected Entity(ID id) {
        if (id == null) throw new IllegalArgumentException("ID must not be null");
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
