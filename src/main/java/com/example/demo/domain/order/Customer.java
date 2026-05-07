package com.example.demo.domain.order;

import com.example.demo.domain.shared.CustomerId;

public record Customer(CustomerId customerId, String customerName) {

    public Customer {
        if (customerId == null) throw new IllegalArgumentException("Customer customerId must not be null");
        if (customerName == null || customerName.isBlank())
            throw new IllegalArgumentException("Customer customerName must not be blank");
    }

    public static Customer of(CustomerId customerId, String customerName) {
        return new Customer(customerId, customerName);
    }
}
