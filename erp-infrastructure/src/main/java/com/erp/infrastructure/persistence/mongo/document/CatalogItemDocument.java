package com.erp.infrastructure.persistence.mongo.document;

public record CatalogItemDocument(
        String id,
        String code,
        String value,
        String description,
        int displayOrder,
        CatalogItemMetadataDocument metadata
) {}
