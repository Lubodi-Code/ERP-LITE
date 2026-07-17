package com.erp.infrastructure.rest.dto;

public record AddressDTO(
        String street,
        String suite,
        String city,
        String zipcode,
        GeoDTO geo
) {}
