package com.fiap.techchallenge4.order.config;

import com.fiap.techchallenge4.order.application.gateways.order.FindAllOrdersGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrderByIdGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrdersByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.impl.CreateOrderUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindAllOrdersUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrderByIdUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrdersByCpfUseCaseImpl;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public FindOrderByIdUseCase findOrderByIdUseCase(final FindOrderByIdGateway findOrderByIdGateway) {
        return new FindOrderByIdUseCaseImpl(findOrderByIdGateway);
    }

    @Bean
    public FindAllOrdersUseCase findAllOrdersUseCase(final FindAllOrdersGateway findAllOrdersGateway) {
        return new FindAllOrdersUseCaseImpl(findAllOrdersGateway);
    }

    @Bean
    public FindOrdersByCpfUseCase findOrdersByCpfUseCase(final FindOrdersByCpfGateway findOrdersByCpfGateway) {
        return new FindOrdersByCpfUseCaseImpl(findOrdersByCpfGateway);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            final OrderRepositoryGateway orderRepositoryGateway,
            final FindProductGateway findProductGateway,
            final OrderKafkaRepositoryGateway orderKafkaRepositoryGateway
    ) {
        return new CreateOrderUseCaseImpl(
                orderRepositoryGateway,
                findProductGateway,
                orderKafkaRepositoryGateway
        );
    }
}
