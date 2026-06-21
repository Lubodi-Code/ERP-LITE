package com.erp.domain.ports.services;

import com.erp.domain.customer.CustomerInfo;
import com.erp.domain.shared.CustomerId;

import java.util.Optional;

public interface CustomerServicePort {

    Optional<CustomerInfo> findById(CustomerId customerId);
}
