package com.fiap.techchallenge4.order.infra.gateways.customer;

import com.fiap.techchallenge4.order.helper.fixture.domain.CustomerDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.exception.FeignExceptionFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderCustomerResponseFixture;
import com.fiap.techchallenge4.order.infra.client.CustomerClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderCustomerResponseMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerClientGatewayTest {
    @InjectMocks
    private CustomerClientGateway customerClientGateway;
    @Mock
    private CustomerClient customerClient;
    @Mock
    private ProviderCustomerResponseMapper providerCustomerResponseMapper;

    @Test
    void shouldReturnEmptyWhenResponseIsNull() {
        final var cpf = CPF;

        when(customerClient.getCustomerByCpf(cpf))
                .thenReturn(null);

        final var result = customerClientGateway.find(cpf);
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(customerClient).getCustomerByCpf(cpf);
        verifyNoInteractions(providerCustomerResponseMapper);
    }

    @Test
    void shouldFindCustomerByCpf() {
        final var cpf = CPF;
        final var customerResponse = ProviderCustomerResponseFixture.FULL();
        final var customer = CustomerDomainFixture.FULL();

        when(customerClient.getCustomerByCpf(cpf))
                .thenReturn(customerResponse);

        when(providerCustomerResponseMapper.toCustomerDomain(customerResponse))
                .thenReturn(customer);

        final var result = customerClientGateway.find(cpf);
        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(customer);

        verify(customerClient).getCustomerByCpf(cpf);
        verify(providerCustomerResponseMapper).toCustomerDomain(customerResponse);
    }

    @Test
    void shouldReturnEmptyWhenCustomerIsNotFound() {
        final var cpf = CPF;

        when(customerClient.getCustomerByCpf(cpf))
                .thenThrow(FeignExceptionFixture.NOT_FOUND());

        final var result = customerClientGateway.find(cpf);
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(customerClient).getCustomerByCpf(cpf);
        verifyNoInteractions(providerCustomerResponseMapper);
    }

    @Test
    void shouldReturnFeignExceptionWhenAnyErrorOccurs() {
        final var cpf = CPF;

        when(customerClient.getCustomerByCpf(cpf))
                .thenThrow(FeignExceptionFixture.INTERNAL_SERVER_ERROR());

        assertThatThrownBy(() -> customerClientGateway.find(cpf))
                .isInstanceOf(FeignException.class);

        verify(customerClient).getCustomerByCpf(cpf);
        verifyNoInteractions(providerCustomerResponseMapper);
    }
}
