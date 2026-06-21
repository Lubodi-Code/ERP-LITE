package com.erp.infrastructure.persistence.mongo.adapter;

import com.erp.domain.catalog.CatalogType;
import com.erp.domain.entities.CatalogRoot;
import com.erp.domain.repositories.CatalogRepository;
import com.erp.infrastructure.persistence.mapper.CatalogMapper;
import com.erp.infrastructure.persistence.mongo.document.CatalogDocument;
import com.erp.infrastructure.persistence.mongo.repository.CatalogMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MongoDB adapter (driven/secondary) for the {@link CatalogRepository} domain port.
 * Translates between the {@link CatalogRoot} aggregate and the {@link CatalogDocument}
 * document model, delegating persistence to {@link CatalogMongoRepository}.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CatalogRepositoryMongoAdapter implements CatalogRepository {

    private final CatalogMongoRepository mongoRepository;
    private final CatalogMapper mapper;

    @Override
    public Optional<CatalogRoot> findById(String id) {
        return mongoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<CatalogRoot> findByType(CatalogType catalogType) {
        return mongoRepository.findByCatalogType(catalogType).map(mapper::toDomain);
    }

    @Override
    public List<CatalogRoot> findAll() {
        return mongoRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<CatalogRoot> findAllActive() {
        return mongoRepository.findAll().stream()
                .filter(CatalogDocument::isActive)
                .map(mapper::toDomain)
                .toList();
    }
}
