package com.erp.infrastructure.persistence.mongo.document;

import java.math.BigDecimal;
import java.util.List;

public record CatalogItemMetadataDocument(
        String icon,
        String color,
        BigDecimal fee,
        List<String> nextStatuses
) {}
