package com.fiap.techchallenge4.order.config;

import com.fiap.techchallenge4.order.application.gateways.customer.FindCustomerByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.customer.FindPaymentInfoByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindAllOrdersGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrderByIdGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrdersByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.gateways.payment.ProcessPaymentGateway;
import com.fiap.techchallenge4.order.application.gateways.product.DecreaseStockGateway;
import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.CreateShippingGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.SimulateShippingGateway;
import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderPaymentUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderShippingUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.ValidateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.impl.CreateOrderUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindAllOrdersUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrderByIdUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrdersByCpfUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.ProcessOrderPaymentUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.ProcessOrderShippingUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.ValidateOrderUseCaseImpl;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderPendingPaymentKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateProcessedOrderKafkaRepositoryGateway;
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
            final CreateOrderKafkaRepositoryGateway createOrderKafkaRepositoryGateway,
            final DecreaseStockGateway decreaseStockGateway,
            final FindCustomerByCpfGateway findCustomerByCpfGateway,
            final SimulateShippingGateway simulateShippingGateway
    ) {
        return new CreateOrderUseCaseImpl(
                orderRepositoryGateway,
                findProductGateway,
                createOrderKafkaRepositoryGateway,
                decreaseStockGateway,
                findCustomerByCpfGateway,
                simulateShippingGateway
        );
    }

    @Bean
    public ValidateOrderUseCase validateOrderUseCase(
            final UpdateOrderStatusGateway updateOrderStatusGateway,
            final CreateOrderPendingPaymentKafkaRepositoryGateway createOrderPendingPaymentKafkaRepositoryGateway
    ) {
        return new ValidateOrderUseCaseImpl(updateOrderStatusGateway, createOrderPendingPaymentKafkaRepositoryGateway);
    }

    @Bean
    public ProcessOrderPaymentUseCase processOrderPaymentUseCase(
            final FindPaymentInfoByCpfGateway findPaymentInfoByCpfGateway,
            final UpdateOrderStatusGateway updateOrderStatusGateway,
            final CreateProcessedOrderKafkaRepositoryGateway createProcessedOrderKafkaRepositoryGateway,
            final ProcessPaymentGateway processPaymentGateway
            ) {
        return new ProcessOrderPaymentUseCaseImpl(
                findPaymentInfoByCpfGateway,
                updateOrderStatusGateway,
                createProcessedOrderKafkaRepositoryGateway,
                processPaymentGateway
        );
    }

    @Bean
    public ProcessOrderShippingUseCase processOrderShippingUseCase(
            final FindCustomerByCpfGateway findCustomerByCpfGateway,
            final CreateShippingGateway createShippingGateway,
            final UpdateOrderStatusGateway updateOrderStatusGateway
    ) {
        return new ProcessOrderShippingUseCaseImpl(
                findCustomerByCpfGateway,
                createShippingGateway,
                updateOrderStatusGateway
        );
    }
}
