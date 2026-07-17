package com.erp.application.usecases.product;

import com.erp.application.command.product.DeactivateProductCommand;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Use case: deactivate a product. It is not deleted — it is marked inactive so
 * it stays out of public listings but remains for historical records.
 *
 * <p>Jira: ERP-203 — As a catalog manager I want to retire a product.
 *
 * <p>Driven by {@link DeactivateProductCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class DeactivateProduct {

    private final ProductRepositoryPort productRepository;
    private final CommandHelper commandHelper;

    public ProductRoot execute(DeactivateProductCommand command) {
        ProductRoot product = commandHelper.findProductById(command.productId());
        // deactivate() fails if the product is already inactive and registers the event.
        product.deactivate();
        ProductRoot saved = productRepository.save(product);
        commandHelper.publishDomainEvents(product);
        log.info("Product deactivated: {}", command.productId());
        return saved;
    }
}
