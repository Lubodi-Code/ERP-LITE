package com.erp.domain.ports;

import com.erp.domain.customer.CustomerInfo;
import com.erp.domain.shared.CustomerId;

import java.util.Optional;

public interface CustomerService {

    Optional<CustomerInfo> findById(CustomerId customerId);
}
