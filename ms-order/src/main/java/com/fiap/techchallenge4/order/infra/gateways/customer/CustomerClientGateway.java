package com.fiap.techchallenge4.order.infra.gateways.customer;

import com.fiap.techchallenge4.order.application.gateways.customer.FindCustomerByCpfGateway;
import com.fiap.techchallenge4.order.domain.entities.customer.Customer;
import com.fiap.techchallenge4.order.infra.client.CustomerClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderCustomerResponseMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CustomerClientGateway implements FindCustomerByCpfGateway {
    private final CustomerClient customerClient;
    private final ProviderCustomerResponseMapper providerCustomerResponseMapper;

    public CustomerClientGateway(CustomerClient customerClient, ProviderCustomerResponseMapper providerCustomerResponseMapper) {
        this.customerClient = customerClient;
        this.providerCustomerResponseMapper = providerCustomerResponseMapper;
    }

    @Override
    public Optional<Customer> find(final String cpf) {
        log.info("Buscando cliente no microsserviço...");
        try {
            return Optional.ofNullable(customerClient.getCustomerByCpf(cpf))
                    .map(providerCustomerResponseMapper::toCustomerDomain);
        } catch (FeignException e) {
            if (e.status() != HttpStatus.NOT_FOUND.value()) {
                throw e;
            }
        }
        return Optional.empty();
    }
}
