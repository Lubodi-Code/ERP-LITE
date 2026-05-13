package com.erp.domain.catalog;

import com.erp.domain.common.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Catalog extends AggregateRoot<String> {

    private final String id;
    private final String name;
    private final CatalogType catalogType;
    private final String description;
    private final List<CatalogItem> items;
    private final boolean isActive;

    public Catalog(String id,
                   String name,
                   CatalogType catalogType,
                   String description,
                   List<CatalogItem> items,
                   boolean isActive) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Catalog ID must not be null or empty");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Catalog name must not be null or empty");
        if (catalogType == null)
            throw new IllegalArgumentException("CatalogType must not be null");

        this.id = id;
        this.name = name;
        this.catalogType = catalogType;
        this.description = description;
        this.items = items != null ? List.copyOf(items) : List.of();
        this.isActive = isActive;
    }

    public Optional<CatalogItem> findItemByCode(String code) {
        return items.stream()
                .filter(item -> item.getCode().equals(code))
                .findFirst();
    }

    public boolean containsItemByCode(String code) {
        return findItemByCode(code).isPresent();
    }

    public List<CatalogItem> findActiveItems() {
        return items.stream()
                .filter(CatalogItem::isActive)
                .toList();
    }

    public List<CatalogItem> findAll() {
        return Collections.unmodifiableList(items);
    }
}
