package com.erp.infrastructure.rest.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final JsonPlaceholderConfigModel jsonConfig;

    private final ClientHttpRequestInterceptor logInterceptor = (request, body, exe) -> {
        log.info("Calling JSON Placeholder API");
        log.info("Method: {}", request.getMethod());
        log.info("URI: {}", request.getURI());
        log.info("Headers: {}", request.getHeaders());

        final long start = System.currentTimeMillis();
        var response = exe.execute(request, body);
        long endTime = System.currentTimeMillis() - start;

        log.info("Execution time: {} ms", endTime);
        log.info("Status: {}", response.getStatusCode());
        return response;
    };

    private final ClientHttpRequestInterceptor errorLogInterceptor = (request, body, exe) -> {
        try {
            return exe.execute(request, body);
        } catch (Exception e) {
            log.error("Error message: {}", e.getMessage());
            throw e;
        }
    };

    @Bean
    @ConditionalOnProperty(prefix = "json-placeholder.api", name = "enabled", havingValue = "true", matchIfMissing = true)
    RestClient jsonPlaceholder() {
        return RestClient.builder()
                .baseUrl(jsonConfig.baseUrl())
                .requestInterceptors(interceptors -> {
                    interceptors.add(logInterceptor);
                    interceptors.add(errorLogInterceptor);
                })
                .defaultHeaders(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }
}
