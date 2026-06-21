package com.erp.infrastructure.persistence.mapper;

import com.erp.domain.entities.OrderRoot;
import com.erp.domain.order.Customer;
import com.erp.domain.order.OrderId;
import com.erp.domain.order.OrderItem;
import com.erp.domain.order.OrderNumber;
import com.erp.domain.order.OrderStatus;
import com.erp.domain.shared.AuditInfo;
import com.erp.domain.shared.CustomerId;
import com.erp.domain.shared.Money;
import com.erp.infrastructure.persistence.jpa.entity.OrderEntity;
import com.erp.infrastructure.persistence.jpa.entity.OrderProductEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Currency;
import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected OrderItemMapper itemMapper;

    public OrderRoot toDomain(OrderEntity e) {
        String currency = e.getCurrency();
        List<OrderItem> items = e.getItems().stream()
                .map(item -> itemMapper.toDomain(item, currency))
                .toList();
        return OrderRoot.rehydrate(
                OrderId.of(e.getId()),
                OrderNumber.of(e.getOrderNumber()),
                Customer.of(CustomerId.of(e.getCustomerId()), e.getCustomerName()),
                OrderStatus.of(e.getStatus()),
                items,
                Money.of(e.getTotalAmount(), Currency.getInstance(currency)),
                new AuditInfo(e.getCreatedBy(), e.getCreatedAt(), e.getUpdatedAt())
        );
    }

    public OrderEntity toEntity(OrderRoot order) {
        return OrderEntity.builder()
                .id(order.getId().value())
                .orderNumber(order.getOrderNumber().value())
                .customerId(order.getCustomer().customerId().value())
                .customerName(order.getCustomer().customerName())
                .createdBy(order.getAuditInfo().createdBy())
                .status(order.getStatus().value())
                .totalAmount(order.getTotalAmount().amount())
                .currency(order.getTotalAmount().currency().getCurrencyCode())
                .createdAt(order.getAuditInfo().createdAt())
                .updatedAt(order.getAuditInfo().updatedAt())
                .items(itemsToEntity(order.getItems()))
                .build();
    }

    // MapStruct generates this using OrderItemMapper.toEntity for each element
    protected abstract List<OrderProductEntity> itemsToEntity(List<OrderItem> items);
}
