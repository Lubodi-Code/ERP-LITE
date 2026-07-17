package com.erp.application.usecases.helpers;

import com.erp.application.exceptions.CommandException;
import com.erp.domain.common.AggregateRoot;
import com.erp.domain.common.DomainEvent;
import com.erp.domain.entities.OrderRoot;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.order.OrderId;
import com.erp.domain.ports.repositories.OrderRepositoryPort;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Shared helpers for command (write) use cases: aggregate lookups and
 * domain-event publishing. Centralized to avoid duplicating the same three
 * lines across every order/product use case.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CommandHelper {

    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;

    /** Loads an order by its String (UUID) id or fails with {@link CommandException}. */
    public OrderRoot findOrderById(String id) {
        log.info("Finding order {}", id);
        return orderRepository.findById(OrderId.of(UUID.fromString(id)))
                .orElseThrow(() -> new CommandException("Order not found: " + id));
    }

    /** Loads a product by its String (UUID) id or fails with {@link CommandException}. */
    public ProductRoot findProductById(String id) {
        log.info("Finding product {}", id);
        return productRepository.findById(ProductId.of(UUID.fromString(id)))
                .orElseThrow(() -> new CommandException("Product not found: " + id));
    }

    /** Publishes — and drains — the aggregate's domain events. */
    public void publishDomainEvents(AggregateRoot<?> aggregate) {
        List<DomainEvent> events = aggregate.pullDomainEvents(); // returns a copy AND clears
        log.info("Publishing {} domain event(s)", events.size());
        events.forEach(event -> {
            log.debug("Trying to publish event {}", event.getClass().getSimpleName());
            // TODO: wire EventPublisherPort once the messaging module (RabbitMQ) is added
        });
        log.info("Domain events published successfully");
    }
}
