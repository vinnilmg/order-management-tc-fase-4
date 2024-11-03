package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.messaging.producer.CreatedOrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderKafkaRepositoryGateway implements CreateOrderGateway {
    private final CreatedOrderProducer createdOrderProducer;

    public CreateOrderKafkaRepositoryGateway(CreatedOrderProducer createdOrderProducer) {
        this.createdOrderProducer = createdOrderProducer;
    }

    @Override
    public Order create(final Order order) {
        log.info("Postando mensagem no tópico de pedidos criados...");
        createdOrderProducer.create(order);
        return order;
    }
}
