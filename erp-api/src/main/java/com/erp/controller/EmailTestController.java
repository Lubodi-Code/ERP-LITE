package com.erp.controller;

import com.erp.domain.order.OrderId;
import com.erp.domain.ports.services.EmailServicePort;
import com.erp.domain.shared.Email;
import com.erp.domain.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Currency;

@RestController
@RequestMapping("/api/test/email")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailServicePort orderMailService;

    @PostMapping("/confirmation")
    public ResponseEntity<String> sendTestConfirmation(@RequestParam String to) {
        Email email = Email.of(to);
        OrderId orderId = OrderId.generate();
        String orderNumber = "ORD-2025-001";
        Money totalAmount = Money.of(new BigDecimal("150.00"), Currency.getInstance("USD"));
        String customerName = "Alejandro Canterón";
        int itemsCount = 10;

        orderMailService.sendMail(email, orderId, orderNumber, totalAmount, customerName, itemsCount);
        return ResponseEntity.ok("Email enviado a: " + to);
    }
}
