package com.erp.infrastructure.persistence.mapper;

import com.erp.domain.vistas.CatalogView;
import com.erp.domain.vistas.ItemsView;
import com.erp.infrastructure.persistence.mongo.document.CatalogDocument;
import com.erp.infrastructure.persistence.mongo.document.CatalogItemDocument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    default CatalogView toCatalogView(CatalogDocument doc) {
        List<ItemsView> items = doc.getItems() != null
                ? doc.getItems().stream()
                    .filter(item -> item.code() != null)
                    .map(this::toItemsView)
                    .toList()
                : List.of();
        return new CatalogView(
                doc.isActive(),
                doc.getName(),
                doc.getDescription(),
                doc.getCatalogType(),
                doc.getCreatedAt(),
                doc.getUpdatedAt(),
                items
        );
    }

    default ItemsView toItemsView(CatalogItemDocument doc) {
        return new ItemsView(
                doc.code(),
                doc.value(),
                doc.description(),
                doc.displayOrder()
        );
    }
}
