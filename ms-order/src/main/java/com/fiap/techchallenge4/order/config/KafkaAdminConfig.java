package com.fiap.techchallenge4.order.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
public class KafkaAdminConfig {
    @Value("${kafka.topic-name}")
    private String topicName;

    private final KafkaProperties properties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic newTopics() {
        return new NewTopic(topicName, 1, (short) 1);
    }
}
