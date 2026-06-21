package com.erp.infrastructure.rest.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "json-placeholder.api")
public record JsonPlaceholderConfigModel(
        @NotBlank String baseUrl,
        @NotNull List<String> endpoints,
        @Positive int connectTimeout,
        @Positive int readTimeout,
        boolean enabled
) {}
