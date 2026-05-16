package com.erp.infrastructure.persistence.mongo.repository;

import com.erp.infrastructure.persistence.mongo.document.AuditLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogMongoRepository extends MongoRepository<AuditLogDocument, String> {

    List<AuditLogDocument> findByUserId(String userId);
}
