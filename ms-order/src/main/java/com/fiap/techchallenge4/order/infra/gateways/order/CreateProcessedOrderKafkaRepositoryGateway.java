package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.messaging.producer.ProcessedOrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateProcessedOrderKafkaRepositoryGateway implements CreateOrderGateway {
    private final ProcessedOrderProducer processedOrderProducer;

    public CreateProcessedOrderKafkaRepositoryGateway(ProcessedOrderProducer processedOrderProducer) {
        this.processedOrderProducer = processedOrderProducer;
    }

    @Override
    public Order create(final Order order) {
        log.info("Postando mensagem no tópico de pedidos processados...");
        processedOrderProducer.create(order);
        return order;
    }
}
