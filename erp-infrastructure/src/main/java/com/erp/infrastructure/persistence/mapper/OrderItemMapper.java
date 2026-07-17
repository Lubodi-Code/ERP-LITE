package com.erp.infrastructure.persistence.mapper;

import com.erp.domain.order.OrderItem;
import com.erp.domain.order.OrderItemId;
import com.erp.domain.product.ProductId;
import com.erp.domain.shared.Money;
import com.erp.domain.shared.Quantity;
import com.erp.infrastructure.persistence.jpa.entity.OrderProductEntity;
import com.erp.infrastructure.persistence.jpa.entity.ProductEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    default OrderItem toDomain(OrderProductEntity e, @Context String currency) {
        return OrderItem.rehydrate(
                toItemId(e.getId()),
                toProductId(e.getProduct().getId()),
                e.getProductName(),
                toQty(e.getQuantity()),
                toMoney(e.getUnitPrice(), currency),
                toMoney(e.getSubtotal(), currency)
        );
    }

    default OrderProductEntity toEntity(OrderItem item) {
        return OrderProductEntity.builder()
                .id(fromItemId(item.getId()))
                .product(ProductEntity.builder().id(fromProductId(item.getProductReference())).build())
                .productName(item.getProductName())
                .quantity(fromQty(item.getQuantity()))
                .unitPrice(fromMoneyAmount(item.getUnitPrice()))
                .subtotal(fromMoneyAmount(item.getSubtotal()))
                .build();
    }

    default OrderItemId toItemId(UUID v) { return OrderItemId.of(v); }
    default UUID fromItemId(OrderItemId v) { return v.value(); }

    default ProductId toProductId(UUID v) { return ProductId.of(v); }
    default UUID fromProductId(ProductId v) { return v.value(); }

    default Quantity toQty(int v) { return Quantity.of(v); }
    default int fromQty(Quantity v) { return v.value(); }

    default Money toMoney(BigDecimal amount, String currency) {
        return Money.of(amount, Currency.getInstance(currency));
    }

    default BigDecimal fromMoneyAmount(Money m) { return m.amount(); }
}
