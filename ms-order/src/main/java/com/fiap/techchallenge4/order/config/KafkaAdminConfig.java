package com.fiap.techchallenge4.order.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
public class KafkaAdminConfig {
    @Value("${kafka.topics.created-order}")
    private String createdOrderTopicName;

    @Value("${kafka.topics.pending-payment}")
    private String orderPendingPaymentTopicName;
    private final KafkaProperties properties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics orderPendingPaymentTopic() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(createdOrderTopicName)
                        .build(),
                TopicBuilder.name(orderPendingPaymentTopicName)
                        .build()
        );
    }
}
