package com.erp.application.usecases.product;

import com.erp.application.command.product.CreateProductCommand;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.ProductImage;
import com.erp.domain.product.ProductName;
import com.erp.domain.product.SKU;
import com.erp.domain.product.Stock;
import com.erp.domain.shared.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Currency;

/**
 * Use case: create a new product.
 *
 * <p>Jira: ERP-201 — As a catalog manager I want to register a product so it
 * can be sold.
 *
 * <p>Driven by {@link CreateProductCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class CreateProduct {

    private final ProductRepositoryPort productRepository;
    private final CommandHelper commandHelper;

    public ProductRoot execute(CreateProductCommand command) {
        log.info("Creating product sku={}", command.sku());

        // TODO (homework): if command.hasImage(), upload it via ImageServicePort and
        // use the returned ProductImage here instead of null.
        ProductImage image = null;

        ProductRoot product = ProductRoot.create(
                SKU.of(command.sku()),
                ProductName.of(command.name()),
                command.description(),
                Money.of(command.price(), Currency.getInstance(command.currency())),
                Stock.of(command.stock()),
                CategoryReference.of(command.categoryId()),
                image,
                command.createdBy());

        ProductRoot saved = productRepository.save(product);
        commandHelper.publishDomainEvents(product);

        log.info("Product created: id={}, sku={}", saved.getId().value(), saved.getSku().value());
        return saved;
    }
}
