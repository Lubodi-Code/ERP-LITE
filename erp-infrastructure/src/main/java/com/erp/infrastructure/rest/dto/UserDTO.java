package com.erp.infrastructure.rest.dto;

public record UserDTO(
        Long id,
        String name,
        String username,
        String email,
        String phone,
        String website,
        AddressDTO address,
        CompanyDTO company
) {}
