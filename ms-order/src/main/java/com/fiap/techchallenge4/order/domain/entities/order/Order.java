package com.fiap.techchallenge4.order.domain.entities.order;

import com.fiap.techchallenge4.order.domain.entities.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface Order extends Serializable {
    Long getId();

    String getCpf();

    String getMaskedCpf();

    String getStatus();

    BigDecimal getTotal();

    LocalDateTime getCreationDate();

    LocalDateTime getCompletionDate();

    List<Product> getProducts();

    String getFormattedTotal();
}
