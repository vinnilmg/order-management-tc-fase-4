package com.fiap.techchallenge4.order.config;

import com.fiap.techchallenge4.order.application.gateways.OrderGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindAllOrdersUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrderByIdUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrdersByCpfUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public FindOrderByIdUseCase findOrderByIdUseCase(final OrderGateway orderGateway) {
        return new FindOrderByIdUseCaseImpl(orderGateway);
    }

    @Bean
    public FindAllOrdersUseCase findAllOrdersUseCase(final OrderGateway orderGateway) {
        return new FindAllOrdersUseCaseImpl(orderGateway);
    }

    @Bean
    public FindOrdersByCpfUseCase findOrdersByCpfUseCase(final OrderGateway orderGateway) {
        return new FindOrdersByCpfUseCaseImpl(orderGateway);
    }
}
