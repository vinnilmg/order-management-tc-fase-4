package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderPaymentUseCase;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.OrderDto;
import com.fiap.techchallenge4.order.infra.messaging.consumer.mappers.OrderModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PendingPaymentOrderConsumer {
    private final ProcessOrderPaymentUseCase processOrderPaymentUseCase;
    private final OrderModelMapper orderModelMapper;

    public PendingPaymentOrderConsumer(ProcessOrderPaymentUseCase processOrderPaymentUseCase, OrderModelMapper orderModelMapper) {
        this.processOrderPaymentUseCase = processOrderPaymentUseCase;
        this.orderModelMapper = orderModelMapper;
    }

    @KafkaListener(
            topics = "${kafka.topics.pending-payment}",
            groupId = "order-creation-group",
            containerFactory = "orderContainerFactory"
    )
    public void pendingPaymentConsumer(@Payload final OrderDto order) {
        log.info("Novo pedido recebido na fila de pagamento, ID: {}", order.getId());
        processOrderPaymentUseCase.process(orderModelMapper.toDomain(order));
    }
}
