package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderShippingUseCase;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.OrderDto;
import com.fiap.techchallenge4.order.infra.messaging.consumer.mappers.OrderDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessedOrderConsumer {
    private final OrderDtoMapper orderDtoMapper;
    private final ProcessOrderShippingUseCase processOrderShippingUseCase;

    public ProcessedOrderConsumer(OrderDtoMapper orderDtoMapper, ProcessOrderShippingUseCase processOrderShippingUseCase) {
        this.orderDtoMapper = orderDtoMapper;
        this.processOrderShippingUseCase = processOrderShippingUseCase;
    }

    @KafkaListener(
            topics = "${kafka.topics.processed-order}",
            groupId = "order-creation-group",
            containerFactory = "orderContainerFactory"
    )
    public void processedOrderConsumer(@Payload final OrderDto order) {
        log.info("Novo pedido recebido na fila de pedidos processados, ID: {}", order.getId());
        processOrderShippingUseCase.process(orderDtoMapper.toDomain(order));
    }
}
