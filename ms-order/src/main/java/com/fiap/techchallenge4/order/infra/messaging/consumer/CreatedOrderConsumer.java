package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.application.usecases.order.ValidateOrderUseCase;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.OrderDto;
import com.fiap.techchallenge4.order.infra.messaging.consumer.mappers.OrderModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreatedOrderConsumer {
    private final ValidateOrderUseCase validateOrderUseCase;
    private final OrderModelMapper orderModelMapper;

    public CreatedOrderConsumer(ValidateOrderUseCase validateOrderUseCase, OrderModelMapper orderModelMapper) {
        this.validateOrderUseCase = validateOrderUseCase;
        this.orderModelMapper = orderModelMapper;
    }

    @KafkaListener(
            topics = "${kafka.topics.created-order}",
            groupId = "order-creation-group",
            containerFactory = "orderContainerFactory"
    )
    public void orderCreationConsumer(@Payload final OrderDto order) {
        log.info("Novo pedido recebido, ID: {}", order.getId());
        validateOrderUseCase.validate(orderModelMapper.toDomain(order));
    }
}
