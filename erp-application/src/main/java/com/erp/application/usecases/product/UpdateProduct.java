package com.erp.application.usecases.product;

import com.erp.application.command.product.UpdateProductCommand;
import com.erp.application.exceptions.CommandException;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.ports.services.ImageServicePort;
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
    private final ImageServicePort imageService;

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

        ProductImage image = resolveImage(command, product.getImage());

        product.update(name, description, price, category, image);
        ProductRoot saved = productRepository.save(product);
        commandHelper.publishDomainEvents(product);

        log.info("Product updated: {}", command.productId());
        return saved;
    }

    /** Si el comando trae imagen nueva, la sube; si no, conserva la actual. */
    private ProductImage resolveImage(UpdateProductCommand command, ProductImage current) {
        if (!command.hasImage()) {
            return current;
        }
        try {
            log.info("Uploading new image {}", command.imageName());
            ProductImage target = ProductImage.of(
                    "https://erp-products.s3.amazonaws.com/" + command.imageName());
            return imageService.upload(target, command.imageName(), command.imageData());
        } catch (Exception e) {
            log.error("Error uploading image {}", command.imageName(), e);
            throw new CommandException("Error uploading product image: " + e.getMessage());
        }
    }
}
