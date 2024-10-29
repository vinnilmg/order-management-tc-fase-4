package com.fiap.techchallenge4.order.infra.messaging.producer;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@RequiredArgsConstructor
@Component
public class CreatedOrderProducer {
    @Value("${kafka.topics.created-order}")
    private String topicName;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public void create(final Order order) {
        kafkaTemplate.send(topicName, order);
    }
}
