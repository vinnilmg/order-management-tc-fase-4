package com.fiap.techchallenge4.order.infra.controllers.request;

import java.util.List;

public record OrderRequest(
        String cpf,
        List<ProductRequest> products
) {
}
