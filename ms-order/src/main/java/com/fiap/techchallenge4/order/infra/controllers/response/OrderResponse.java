package com.fiap.techchallenge4.order.infra.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String cpf,
        String status,
        BigDecimal total,
        String creationDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String completionDate
) {
}
