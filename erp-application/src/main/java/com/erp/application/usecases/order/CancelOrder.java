package com.erp.application.usecases.order;

import com.erp.application.command.order.CancelOrderCommand;
import com.erp.application.usecases.helpers.CommandHelper;
import com.erp.domain.entities.OrderRoot;
import com.erp.domain.ports.repositories.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Use case: cancel an order. The order is not deleted — it transitions to the
 * CANCELLED state (orders are kept for historical/audit purposes).
 *
 * <p>Jira: ERP-103 — As a sales operator I want to cancel an order with a
 * reason so the cancellation is auditable.
 *
 * <p>Driven by {@link CancelOrderCommand}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional  // TODO: enable once erp-application depends on spring-tx (module wiring)
public class CancelOrder {

    private final OrderRepositoryPort orderRepository;
    private final CommandHelper commandHelper;

    public OrderRoot execute(CancelOrderCommand command) {
        OrderRoot order = commandHelper.findOrderById(command.orderId());
        // order.cancel validates the transition (final states cannot be cancelled)
        // and registers the OrderCancelled event.
        order.cancel(command.reason());
        OrderRoot saved = orderRepository.save(order);
        commandHelper.publishDomainEvents(order);
        log.info("Order cancelled: {} (reason: {})", command.orderId(), command.reason());
        return saved;
    }
}
