package com.erp.infrastructure.rest.adapter;

import com.erp.domain.customer.CustomerInfo;
import com.erp.domain.ports.services.CustomerProviderServicePort;
import com.erp.infrastructure.rest.config.JsonPlaceholderConfigModel;
import com.erp.infrastructure.rest.dto.UserDTO;
import com.erp.infrastructure.rest.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Slf4j
@Service
public class JsonPlaceholderCustomerProviderAdapter implements CustomerProviderServicePort {

    private final RestClient jsonClient;
    private final UserMapper customerMapper;
    private final String endpoint;

    public JsonPlaceholderCustomerProviderAdapter(
            @Qualifier("jsonPlaceholder") RestClient jsonClient,
            UserMapper customerMapper,
            JsonPlaceholderConfigModel jsonConfig) {
        this.jsonClient = jsonClient;
        this.customerMapper = customerMapper;
        this.endpoint = jsonConfig.endpoints().get(0);
    }

    @Override
    public boolean existsById(Long id) {
        log.info("existsById: {}", id);
        return findById(id).isPresent();
    }

    @Override
    public Optional<CustomerInfo> findById(Long id) {
        log.info("findById: {}", id);
        try {
            final UserDTO response = jsonClient
                    .get()
                    .uri(endpoint + "/{id}", id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) ->
                            log.error("Client error on findById: status={}", res.getStatusCode()))
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) ->
                            log.error("Server side error on findById: status={}", res.getStatusCode()))
                    .body(UserDTO.class);

            if (response == null) {
                log.warn("User not found for id: {}", id);
                return Optional.empty();
            }

            log.info("User found: {}", response);
            return Optional.of(customerMapper.toCustomerInfo(response));

        } catch (RestClientException e) {
            log.error("Error on findById while calling API", e);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error on findById", e);
            return Optional.empty();
        }
    }
}
