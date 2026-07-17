package com.erp.domain.ports.services;

import com.erp.domain.common.DomainEvent;

public interface EventPublisherPort {

    void publish(DomainEvent event);
}
