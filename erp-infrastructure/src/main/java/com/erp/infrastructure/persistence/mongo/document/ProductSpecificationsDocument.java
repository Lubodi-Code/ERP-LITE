package com.erp.infrastructure.persistence.mongo.document;

public record ProductSpecificationsDocument(
        String processor,
        String ram,
        String storage,
        String display,
        String weight
) {}
