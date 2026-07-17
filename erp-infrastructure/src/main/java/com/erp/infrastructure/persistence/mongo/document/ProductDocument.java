package com.erp.infrastructure.persistence.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
@Document(collection = "product_documents")
public class ProductDocument {

    @Id
    private String id;
    private boolean active;
    private String categoryId;
    private String categoryName;
    private Instant createdAt;
    private String currency;
    private String description;
    private String imageUrl;
    private String name;
    private BigDecimal price;
    private String sku;
    private ProductSpecificationsDocument specifications;
    private int stock;
    private List<String> tags;
    private Instant updatedAt;
}
