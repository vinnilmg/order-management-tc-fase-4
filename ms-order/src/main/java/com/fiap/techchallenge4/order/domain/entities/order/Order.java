package com.fiap.techchallenge4.order.domain.entities.order;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface Order extends Serializable {
    Long getId();

    String getCpf();

    String getMaskedCpf();

    String getStatus();

    LocalDateTime getCreationDate();

    LocalDateTime getCompletionDate();

    List<Product> getProducts();

    Shipping getShipping();

    BigDecimal getTotal();

    String getFormattedTotal();

    String getFormattedTotalWithShipping();

    BigDecimal getTotalWithShipping();

    void updateShippingInfo(Shipping shipping);

    void updateToPendingPayment();

    void updateToProcessed();

    void updateToWaitShipping();

    void updateToCanceled();
}
