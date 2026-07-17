package com.erp.application.usecases.product;

import com.erp.application.command.product.CreateProductCommand;
import com.erp.application.exceptions.CommandException;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.ports.services.ImageServicePort;
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
    private final ImageServicePort imageService;

    public ProductRoot execute(CreateProductCommand command) {
        log.info("Creating product sku={}", command.sku());

        ProductImage image = uploadImage(command);

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

    /** Sube la imagen si el comando la trae; devuelve null si no hay imagen. */
    private ProductImage uploadImage(CreateProductCommand command) {
        if (!command.hasImage()) {                       // caso borde: sin imagen
            log.info("No image provided for sku={}", command.sku());
            return null;
        }
        try {
            log.info("Uploading image {}", command.imageName());
            // ⚠️ URL base hardcodeada por ahora; lo ideal es que venga de la config de S3,
            // no de la capa de aplicación. Lo dejamos así para avanzar.
            ProductImage target = ProductImage.of(
                    "https://erp-products.s3.amazonaws.com/" + command.imageName());
            return imageService.upload(target, command.imageName(), command.imageData());
        } catch (Exception e) {                           // si la subida falla
            log.error("Error uploading image {}", command.imageName(), e);
            throw new CommandException("Error uploading product image: " + e.getMessage());
        }
    }
}
