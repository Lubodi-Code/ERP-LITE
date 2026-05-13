package com.erp.domain.catalog;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

import com.erp.domain.common.Entity;

@Getter
@ToString
public class CatalogItem extends Entity<String> {

    private final String code;
    private final String value;
    private final String description;
    private final Integer displayOrder;
    private final Map<String, Object> metadata;
    private boolean active;

    public CatalogItem(String id, String code, String value, String description,
                       Integer displayOrder, Map<String, Object> metadata) {
        super(id);
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Code must not be null or empty");
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Value must not be null or empty");

        this.code = code;
        this.value = value;
        this.description = description;
        this.displayOrder = displayOrder;
        this.metadata = metadata;
        this.active = true;
    }

    public Object getMetadata(String key) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException("Metadata key must not be null or empty");
        if (metadata == null) throw new IllegalStateException("Metadata map is not initialized");
        return metadata.get(key);
    }

    public boolean hasMetadata(String key) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException("Metadata key must not be null or empty");
        return metadata != null && metadata.containsKey(key);
    }

    public void turnOffStatus() {
        this.active = false;
    }
}