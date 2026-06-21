package com.erp.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyDTO(
        String name,
        String catchPhrase,
        @JsonProperty("bs") String cp
) {}
