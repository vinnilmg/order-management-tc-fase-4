package com.fiap.techchallenge4.ms_customer.config;

import com.fiap.techchallenge4.ms_customer.application.gateways.AddressGateway;
import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.gateways.PaymentGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.*;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerGateway customerGateway) {
        return new CreateCustomerUseCaseImpl(customerGateway);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(CustomerGateway customerGateway,
                                                       FindCustomerByIdUseCase findCustomerByIdUseCase) {
        return new DeleteCustomerUseCaseImpl(customerGateway, findCustomerByIdUseCase);
    }

    @Bean
    public FindAllCustomersUseCase findAllCustomersUseCase(CustomerGateway customerGateway) {
        return new FindAllCustomersUseCaseImpl(customerGateway);
    }

    @Bean
    public FindCustomerByIdUseCase findCustomerByIdUseCase(CustomerGateway customerGateway) {
        return new FindCustomerByIdUseCaseImpl(customerGateway);
    }

    @Bean
    public FindPaymentByCustomerIdUseCase findPaymentByCustomerIdUseCase(PaymentGateway paymentGateway) {
        return new FindPaymentByCustomerIdUseCaseImpl(paymentGateway);
    }

    @Bean
    public FindCustomerByCpfUseCase findCustomerByCpfUseCase(CustomerGateway customerGateway) {
        return new FindCustomerByCpfUseCaseImpl(customerGateway);
    }

    @Bean UpdateCustomerUseCase updateCustomerUseCase(CustomerGateway customerGateway) {
        return new UpdateCustomerUseCaseImpl(customerGateway);
    }


}
