package com.erp.domain;

import com.erp.domain.order.Customer;
import com.erp.domain.shared.CustomerId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private static final CustomerId CUSTOMER_ID = CustomerId.of(1L);

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_shouldCreateCustomer_whenValidArguments() {
        Customer customer = new Customer(CUSTOMER_ID, "John Doe");

        assertEquals(CUSTOMER_ID, customer.customerId());
        assertEquals("John Doe", customer.customerName());
    }

    @Test
    void constructor_shouldThrow_whenCustomerIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, "John Doe"));
    }

    @Test
    void constructor_shouldThrow_whenCustomerNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Customer(CUSTOMER_ID, null));
    }

    @Test
    void constructor_shouldThrow_whenCustomerNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Customer(CUSTOMER_ID, "  "));
    }

    // -------------------------------------------------------------------------
    // Factory method
    // -------------------------------------------------------------------------

    @Test
    void of_shouldCreateCustomer_whenValidArguments() {
        Customer customer = Customer.of(CUSTOMER_ID, "Jane Smith");

        assertEquals(CUSTOMER_ID, customer.customerId());
        assertEquals("Jane Smith", customer.customerName());
    }

    @Test
    void of_shouldThrow_whenCustomerIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Customer.of(null, "Jane"));
    }
}
