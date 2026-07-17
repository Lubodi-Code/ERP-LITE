package com.erp.application.usecases.product;

import com.erp.application.command.product.UpdateStockCommand;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Use case: adjust a product's stock. Positive quantity increments, negative
 * decrements (the command forbids zero).
 *
 * <p>Jira: ERP-204 — As a warehouse operator I want to adjust stock levels with
 * a reason for the change.
 *
 * <p>Driven by {@link UpdateStockCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class UpdateStock {

    private final ProductRepositoryPort productRepository;
    private final CommandHelper commandHelper;

    public ProductRoot execute(UpdateStockCommand command) {
        ProductRoot product = commandHelper.findProductById(command.productId());

        if (command.isIncrement()) {
            product.incrementStock(command.absoluteQuantity(), command.reason());
        } else {
            product.decrementStock(command.absoluteQuantity(), command.reason());
        }

        ProductRoot saved = productRepository.save(product);
        commandHelper.publishDomainEvents(product);
        log.info("Stock updated for {}: {} ({})",
                command.productId(), command.quantity(), command.reason());
        return saved;
    }
}
