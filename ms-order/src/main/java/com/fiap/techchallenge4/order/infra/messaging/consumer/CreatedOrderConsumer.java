package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.infra.messaging.consumer.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreatedOrderConsumer {

    @KafkaListener(
            topics = "${kafka.topics.created-order}",
            groupId = "order-creation-group",
            containerFactory = "orderContainerFactory"
    )
    public void orderCreationConsumer(@Payload final OrderModel order) {
        log.info("Novo pedido recebido: {}", order.getId());

        log.info("Validando pedido...");
        final var isValid = order.validate();

        if (isValid) {
            log.info("Pedido válido, enviando para fila de pagamento...");
            // TODO: Envia para a fila de pagamento
        } else {
            log.info("Pedido inválido, o mesmo não será processado.");
        }
    }
}
