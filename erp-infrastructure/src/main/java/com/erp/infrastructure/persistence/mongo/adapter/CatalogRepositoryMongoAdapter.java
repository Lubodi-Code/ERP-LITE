package com.erp.infrastructure.persistence.mongo.adapter;

import com.erp.domain.repositories.CatalogRepository;
import com.erp.domain.vistas.CatalogView;
import com.erp.domain.vistas.ItemsView;
import com.erp.infrastructure.persistence.mapper.CatalogMapper;
import com.erp.infrastructure.persistence.mongo.document.CatalogDocument;
import com.erp.infrastructure.persistence.mongo.document.CatalogItemDocument;
import com.erp.infrastructure.persistence.mongo.repository.CatalogMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CatalogRepositoryMongoAdapter implements CatalogRepository {

    private final CatalogMongoRepository mongoRepository;
    private final CatalogMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<CatalogView> findByType(String type) {
        return mongoRepository.findByCatalogType(type)
                .map(mapper::toCatalogView);
    }

    @Override
    public Optional<ItemsView> findByTypeAndCode(String type, String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("catalogType").is(type));
        query.addCriteria(Criteria.where("items.code").is(code));
        query.fields().include("items.$");

        CatalogDocument doc = mongoTemplate.findOne(query, CatalogDocument.class);
        if (doc != null && doc.getItems() != null) {
            return doc.getItems().stream()
                    .filter(item -> item.code().equals(code))
                    .map(mapper::toItemsView)
                    .findFirst();
        }
        return Optional.empty();
    }
}
