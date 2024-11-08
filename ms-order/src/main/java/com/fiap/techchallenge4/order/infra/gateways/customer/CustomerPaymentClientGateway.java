package com.fiap.techchallenge4.order.infra.gateways.customer;

import com.fiap.techchallenge4.order.application.gateways.customer.FindPaymentInfoByCpfGateway;
import com.fiap.techchallenge4.order.domain.entities.customer.payment.Payment;
import com.fiap.techchallenge4.order.infra.client.CustomerClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderPaymentInfoResponseMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CustomerPaymentClientGateway implements FindPaymentInfoByCpfGateway {
    private final CustomerClient customerClient;
    private final ProviderPaymentInfoResponseMapper providerPaymentInfoResponseMapper;

    public CustomerPaymentClientGateway(CustomerClient customerClient, ProviderPaymentInfoResponseMapper providerPaymentInfoResponseMapper) {
        this.customerClient = customerClient;
        this.providerPaymentInfoResponseMapper = providerPaymentInfoResponseMapper;
    }

    @Override
    public Optional<Payment> find(final String cpf) {
        log.info("Buscando forma de pagamento do cliente no microsserviço...");
        try {
            return Optional.ofNullable(customerClient.getPaymentInfo(cpf))
                    .map(providerPaymentInfoResponseMapper::toPaymentDomain);
        } catch (FeignException e) {
            if (e.status() != HttpStatus.NOT_FOUND.value()) {
                throw e;
            }
        }
        return Optional.empty();
    }
}
