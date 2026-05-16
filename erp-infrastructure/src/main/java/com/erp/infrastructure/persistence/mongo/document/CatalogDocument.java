package com.erp.infrastructure.persistence.mongo.document;

import com.erp.domain.catalog.CatalogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "catalogs")
public class CatalogDocument {

    @Id
    private String id;
    private boolean active;
    private CatalogType catalogType;
    private Instant createdAt;
    private String description;
    private List<CatalogItemDocument> items;
    private String name;
    private Instant updatedAt;
}
