package com.erp.domain.customer;

/** 
 * CustomerInfo is a simple immutable data transfer object (DTO) that encapsulates all relevant information about a customer.
 * It includes fields such as id, name, email, phone, address, city, state, zipcode, country, and companyName. The constructor validates that all fields are non-null and non
 * Value object inmutable, for JSONPlaceholder API
 */
public record CustomerInfo(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        String city,
        String state,
        String zipcode,
        String country,
        String companyName
) {

    public CustomerInfo {
        if (id == null) throw new IllegalArgumentException("Customer ID must not be null");
        requireNonBlank(name, "name");
        requireNonBlank(email, "email");
        requireNonBlank(phone, "phone");
        requireNonBlank(address, "address");
        requireNonBlank(city, "city");
        requireNonBlank(state, "state");
        requireNonBlank(zipcode, "zipcode");
        requireNonBlank(country, "country");
        requireNonBlank(companyName, "companyName");
    }

    private static void requireNonBlank(String value, String field) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Customer " + field + " must not be null or empty");
    }
}
