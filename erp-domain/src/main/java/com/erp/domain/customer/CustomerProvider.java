package com.erp.domain.customer;

import java.util.Optional;

public interface CustomerProvider {

    Optional<CustomerInfo> findById(Long id);

    default boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}
