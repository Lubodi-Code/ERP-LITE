package com.erp.domain.ports;

import com.erp.domain.common.DomainEvent;

public interface EventPublisher {

    void publish(DomainEvent event);
}
