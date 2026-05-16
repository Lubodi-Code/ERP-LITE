package com.erp.domain.ports;

import com.erp.domain.order.Order;

public interface EmailService {

    void sendOrderConfirmation(Order order);

    void sendOrderShipped(Order order);

    void sendOrderDelivered(Order order);
}
