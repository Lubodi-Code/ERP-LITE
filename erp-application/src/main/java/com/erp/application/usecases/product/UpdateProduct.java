package com.erp.application.usecases.product;

import com.erp.application.command.product.UpdateProductCommand;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.product.CategoryReference;
import com.erp.domain.product.ProductImage;
import com.erp.domain.product.ProductName;
import com.erp.domain.shared.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Use case: update an existing product. Only the fields provided in the command
 * are changed; the rest keep their current value.
 *
 * <p>Jira: ERP-202 — As a catalog manager I want to edit a product's details.
 *
 * <p>Driven by {@link UpdateProductCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class UpdateProduct {

    private final ProductRepositoryPort productRepository;
    private final CommandHelper commandHelper;

    public ProductRoot execute(UpdateProductCommand command) {
        ProductRoot product = commandHelper.findProductById(command.productId());

        ProductName name = command.shouldUpdateName()
                ? ProductName.of(command.name()) : product.getName();
        String description = command.description() != null
                ? command.description() : product.getDescription();
        Money price = command.shouldUpdatePrice()
                ? Money.of(command.price(), product.getPrice().currency()) : product.getPrice();
        CategoryReference category = command.shouldUpdateCategory()
                ? CategoryReference.of(command.categoryId()) : product.getCategory();

        // TODO (homework): if command.hasImage(), upload the new image via ImageServicePort.
        ProductImage image = product.getImage();

        product.update(name, description, price, category, image);
        ProductRoot saved = productRepository.save(product);
        commandHelper.publishDomainEvents(product);

        log.info("Product updated: {}", command.productId());
        return saved;
    }
}
