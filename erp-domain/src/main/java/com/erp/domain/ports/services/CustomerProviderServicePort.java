package com.erp.domain.ports.services;

import com.erp.domain.customer.CustomerInfo;

import java.util.Optional;

/**
 * Port: provides access to external customer data (e.g. JSONPlaceholder).
 */
public interface CustomerProviderServicePort {

    Optional<CustomerInfo> findById(Long id);

    default boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}
