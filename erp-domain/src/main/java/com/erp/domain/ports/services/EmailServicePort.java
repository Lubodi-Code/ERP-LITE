package com.erp.domain.ports.services;

import com.erp.domain.order.OrderId;
import com.erp.domain.shared.Email;
import com.erp.domain.shared.Money;

/**
 * Port: sends order-related emails (e.g. order confirmation).
 * Carries the values needed to fill the HTML template placeholders.
 */
public interface EmailServicePort {

    void sendMail(Email email,
                  OrderId orderId,
                  String orderNumber,
                  Money totalAmount,
                  String customerName,
                  int itemsCount);
}
