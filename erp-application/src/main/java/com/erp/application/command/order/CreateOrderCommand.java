package com.erp.application.command.order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Command to create a new order.
 *
 * @param customerId External customer id (reference to JSONPlaceholder)
 * @param createdBy  Username of the user creating the order
 * @param items      Items to include in the order (at least one)
 */
public record CreateOrderCommand(

        @NotNull(message = "Customer id cannot be null")
        @Positive(message = "Customer id must be greater than 0")
        Long customerId,

        @NotBlank(message = "Created by cannot be null or blank")
        String createdBy,

        @NotEmpty(message = "Order must have at least one item")
        @Valid
        List<OrderItemRequest> items
) {

    /**
     * Item line of a create-order request. Flattened: the product is
     * referenced by its id, the use case resolves price/name from the domain.
     *
     * @param productId Product id (UUID as String)
     * @param quantity  Quantity to order (must be > 0)
     */
    public record OrderItemRequest(

            @NotBlank(message = "Product id cannot be null or blank")
            String productId,

            @NotNull(message = "Quantity cannot be null")
            @Min(value = 1, message = "Quantity must be greater than 0")
            Integer quantity
    ) {}

    /**
     * Total number of item lines in this order.
     */
    public int itemsCount() {
        return items != null ? items.size() : 0;
    }
}
