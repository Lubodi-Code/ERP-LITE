package com.erp.application.usecases.order;

import com.erp.application.command.order.UpdateOrderStatusCommand;
import com.erp.application.exceptions.CommandException;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.OrderRoot;
import com.erp.domain.ports.repositories.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Use case: advance an existing order's status (CONFIRMED, SHIPPED, DELIVERED).
 *
 * <p>Jira: ERP-102 — As a fulfillment operator I want to move an order through
 * its lifecycle so customers see accurate progress. Cancelling has its own use
 * case (it requires a reason).
 *
 * <p>Driven by {@link UpdateOrderStatusCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class UpdateOrderStatus {

    private final OrderRepositoryPort orderRepository;
    private final CommandHelper commandHelper;

    public OrderRoot execute(UpdateOrderStatusCommand command) {
        OrderRoot order = commandHelper.findOrderById(command.orderId());
        updateStatus(order, command.newStatus());
        OrderRoot saved = orderRepository.save(order);
        commandHelper.publishDomainEvents(order);
        log.info("Order {} status updated to {}", command.orderId(), command.newStatus());
        return saved;
    }

    /**
     * Translates a status string into the matching domain behavior.
     * The aggregate itself validates the transition and registers the event.
     */
    private void updateStatus(OrderRoot order, String status) {
        switch (status.toUpperCase()) {
            case "CONFIRMED" -> order.confirm();
            case "SHIPPED"   -> order.ship();
            case "DELIVERED" -> order.deliver();
            default -> throw new CommandException("Invalid status: " + status);
        }
    }
}
