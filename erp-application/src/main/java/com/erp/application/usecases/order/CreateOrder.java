package com.erp.application.usecases.order;

import com.erp.application.command.order.CreateOrderCommand;
import com.erp.application.exceptions.CommandException;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.customer.CustomerInfo;
import com.erp.domain.entities.OrderRoot;
import com.erp.domain.entities.ProductRoot;
import com.erp.domain.order.Customer;
import com.erp.domain.order.OrderItem;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.ports.repositories.OrderRepositoryPort;
import com.erp.domain.ports.repositories.ProductRepositoryPort;
import com.erp.domain.ports.services.CustomerProviderServicePort;
import com.erp.domain.ports.services.EmailServicePort;
import com.erp.domain.product.ProductId;
import com.erp.domain.shared.CustomerId;
import com.erp.domain.shared.Email;
import com.erp.domain.shared.Quantity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Use case: create a new order.
 *
 * <p>Jira: ERP-101 — As a sales operator I want to register a customer order
 * so its products are reserved and the order enters the fulfillment pipeline.
 *
 * <p>Orchestrates the domain: validates the customer, resolves each product,
 * builds the {@link OrderRoot} aggregate, persists it and publishes its domain
 * events. Driven by {@link CreateOrderCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class CreateOrder {

    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;
    private final CustomerProviderServicePort customerProvider;
    private final EmailServicePort emailService;
    private final CommandHelper commandHelper;

    /**
     * Creates and persists an order from the given command.
     *
     * @return the persisted order (rehydrated, with its generated ids)
     * @throws CommandException if the customer or any product does not exist
     */
    public OrderRoot execute(CreateOrderCommand command) {
        log.info("Creating order for customer {} with {} item(s)",
                command.customerId(), command.itemsCount());

        Customer customer = resolveCustomer(command.customerId());
        List<OrderItem> items = command.items().stream()
                .map(this::toOrderItem)
                .toList();

        OrderRoot order = OrderRoot.create(generateOrderNumber(), customer, items, command.createdBy());
        OrderRoot saved = orderRepository.save(order);

        // Events live on the in-memory aggregate, not on the rehydrated `saved`.
        commandHelper.publishDomainEvents(order);

        // sendMail(order, customer); // enable to actually send the confirmation email
        log.info("Order created: id={}, number={}",
                saved.getId().value(), saved.getOrderNumber().value());
        return saved;
    }

    /** Validates the external customer exists and builds the domain {@link Customer}. */
    private Customer resolveCustomer(Long customerId) {
        CustomerInfo info = customerProvider.findById(customerId)
                .orElseThrow(() -> new CommandException("Customer not found: " + customerId));
        return Customer.of(CustomerId.of(customerId), info.name());
    }

    /** Resolves a product and turns a request line into a domain {@link OrderItem}. */
    private OrderItem toOrderItem(CreateOrderCommand.OrderItemRequest req) {
        ProductId productId = ProductId.of(UUID.fromString(req.productId()));
        ProductRoot product = productRepository.findById(productId)
                .orElseThrow(() -> new CommandException("Product not found: " + req.productId()));
        // OrderItem.from validates the product is active and has stock, and snapshots price/name.
        return OrderItem.from(product, Quantity.of(req.quantity()));
    }

    /** Generates the order number for a new order. */
    private OrderNumber generateOrderNumber() {
        // ponytail: OrderNumber.generate() already builds ORD-YYYY-NNN with a random
        // sequence; no manual System.currentTimeMillis()%1000 helper needed.
        return OrderNumber.generate();
    }

    /** Sends the order-confirmation email. Kept off by default to avoid emailing on every run. */
    private void sendMail(OrderRoot order, Customer customer) {
        try {
            // Placeholder recipient: JSONPlaceholder customer emails are not deliverable.
            // In production, use the customer's verified email.
            Email recipient = Email.of("customer" + customer.customerId().value() + "@example.com");
            log.info("Sending confirmation email to {}", recipient.value());
            emailService.sendMail(
                    recipient,
                    order.getId(),
                    order.getOrderNumber().value(),
                    order.getTotalAmount(),
                    customer.customerName(),
                    order.getItems().size());
        } catch (Exception e) {
            log.error("Error sending confirmation email for order {}", order.getId().value(), e);
            throw new CommandException("Error sending confirmation email: " + e.getMessage());
        }
    }
}
