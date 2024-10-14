package com.fiap.techchallenge4.order.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Order {
    Long getId();

    String getCpf();

    String getMaskedCpf();

    String getStatus();

    BigDecimal getTotal();

    LocalDateTime getCreationDate();

    LocalDateTime getCompletionDate();
}
