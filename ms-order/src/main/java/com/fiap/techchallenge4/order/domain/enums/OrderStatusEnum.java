package com.fiap.techchallenge4.order.domain.enums;

import java.util.Arrays;

public enum OrderStatusEnum {
    CRIADO,
    PROCESSADO,
    PENDENTE_PAGAMENTO,
    EM_ROTA_ENTREGA,
    FINALIZADO;

    public static OrderStatusEnum of(final String status) {
        return Arrays.stream(values())
                .filter(statusEnum -> statusEnum.name().equalsIgnoreCase(status))
                .findAny()
                .orElse(null);
    }
}
