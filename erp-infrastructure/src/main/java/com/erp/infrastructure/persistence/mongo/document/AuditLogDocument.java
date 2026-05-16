package com.erp.infrastructure.persistence.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "audit_logs")
public class AuditLogDocument {

    @Id
    private String id;
    private String className;
    private String endpoint;
    private String errorMessage;
    private long executionTimeMs;
    private String ipAddress;
    private String methodName;
    private boolean success;
    private Instant timestamp;
    private String userId;
}
