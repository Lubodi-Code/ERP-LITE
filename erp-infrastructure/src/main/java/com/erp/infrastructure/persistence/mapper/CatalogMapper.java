package com.erp.infrastructure.persistence.mapper;

import com.erp.domain.catalog.CatalogItem;
import com.erp.domain.entities.CatalogRoot;
import com.erp.infrastructure.persistence.mongo.document.CatalogDocument;
import com.erp.infrastructure.persistence.mongo.document.CatalogItemDocument;
import com.erp.infrastructure.persistence.mongo.document.CatalogItemMetadataDocument;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    default CatalogRoot toDomain(CatalogDocument doc) {
        List<CatalogItem> items = doc.getItems() != null
                ? doc.getItems().stream().map(this::toDomainItem).toList()
                : List.of();
        return new CatalogRoot(
                doc.getId(),
                doc.getName(),
                doc.getCatalogType(),
                doc.getDescription(),
                items,
                doc.isActive()
        );
    }

    default CatalogDocument toDocument(CatalogRoot catalog) {
        List<CatalogItemDocument> items = catalog.getItems().stream()
                .map(this::toDocumentItem)
                .toList();
        return CatalogDocument.builder()
                .id(catalog.getId())
                .name(catalog.getName())
                .catalogType(catalog.getCatalogType())
                .description(catalog.getDescription())
                .items(items)
                .active(catalog.isActive())
                .build();
    }

    default CatalogItem toDomainItem(CatalogItemDocument doc) {
        return new CatalogItem(
                doc.id(),
                doc.code(),
                doc.value(),
                doc.description(),
                doc.displayOrder(),
                toMetadataMap(doc.metadata())
        );
    }

    default CatalogItemDocument toDocumentItem(CatalogItem item) {
        return new CatalogItemDocument(
                item.getId(),
                item.getCode(),
                item.getValue(),
                item.getDescription(),
                item.getDisplayOrder(),
                toMetadataDocument(item.getMetadata())
        );
    }

    default Map<String, Object> toMetadataMap(CatalogItemMetadataDocument meta) {
        if (meta == null) return Map.of();
        Map<String, Object> map = new HashMap<>();
        if (meta.icon() != null) map.put("icon", meta.icon());
        if (meta.color() != null) map.put("color", meta.color());
        if (meta.fee() != null) map.put("fee", meta.fee());
        if (meta.nextStatuses() != null) map.put("nextStatuses", meta.nextStatuses());
        return Collections.unmodifiableMap(map);
    }

    default CatalogItemMetadataDocument toMetadataDocument(Map<String, Object> metadata) {
        if (metadata == null) return new CatalogItemMetadataDocument(null, null, null, null);
        String icon = (String) metadata.get("icon");
        String color = (String) metadata.get("color");
        Object feeObj = metadata.get("fee");
        BigDecimal fee = feeObj != null ? new BigDecimal(feeObj.toString()) : null;
        @SuppressWarnings("unchecked")
        List<String> nextStatuses = (List<String>) metadata.get("nextStatuses");
        return new CatalogItemMetadataDocument(icon, color, fee, nextStatuses);
    }
}
