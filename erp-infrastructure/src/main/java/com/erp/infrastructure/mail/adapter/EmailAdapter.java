package com.erp.infrastructure.mail.adapter;

import com.erp.domain.order.OrderId;
import com.erp.domain.ports.services.EmailServicePort;
import com.erp.domain.shared.Email;
import com.erp.domain.shared.EmailDeliveryException;
import com.erp.domain.shared.Money;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAdapter implements EmailServicePort {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("classpath:templates/email-travel-confirmation-template.html")
    private Resource mailTemplate;

    @Value("${email.company:ERP Lite}")
    private String companyName;

    @Override
    public void sendMail(Email email,
                         OrderId orderId,
                         String orderNumber,
                         Money totalAmount,
                         String customerName,
                         int itemsCount) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(email.value());
            helper.setSubject("Orden " + orderNumber + " confirmada");

            final String html = buildTemplate(orderNumber, orderId, totalAmount, customerName, itemsCount);
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Email send success to={} orderNumber={}", email.value(), orderNumber);
        } catch (MessagingException e) {
            log.error("Error sending mail to={} orderNumber={}", email.value(), orderNumber, e);
            throw new EmailDeliveryException("Error sending mail to " + email.value(), e);
        }
    }

    private String buildTemplate(String orderNumber,
                                 OrderId orderId,
                                 Money totalAmount,
                                 String customerName,
                                 int itemsCount) {
        try {
            String html = mailTemplate.getContentAsString(StandardCharsets.UTF_8);
            return html
                    .replace("(customerName)", customerName)
                    .replace("(orderNumber)", orderNumber)
                    .replace("(orderId)", orderId.value().toString())
                    .replace("(itemsCount)", String.valueOf(itemsCount))
                    .replace("(totalAmount)", totalAmount.amount().toString())
                    .replace("(currency)", totalAmount.currency().getCurrencyCode())
                    .replace("(companyName)", companyName);
        } catch (IOException e) {
            log.error("Error reading mail template", e);
            throw new EmailDeliveryException("Could not read mail template", e);
        }
    }
}
