package com.fiap.techchallenge4.order.infra.gateways.customer;

import com.fiap.techchallenge4.order.helper.fixture.domain.PaymentDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.exception.FeignExceptionFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderPaymentInfoResponseFixture;
import com.fiap.techchallenge4.order.infra.client.CustomerClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderPaymentInfoResponseMapper;
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
 class CustomerPaymentClientGatewayTest {
    @InjectMocks
    private CustomerPaymentClientGateway customerPaymentClientGateway;
    @Mock
    private CustomerClient customerClient;
    @Mock
    private ProviderPaymentInfoResponseMapper providerPaymentInfoResponseMapper;

    @Test
    void shouldReturnEmptyWhenResponseIsNull() {
        final var cpf = CPF;

        when(customerClient.getPaymentInfo(cpf))
                .thenReturn(null);

        final var result = customerPaymentClientGateway.find(cpf);
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(customerClient).getPaymentInfo(cpf);
        verifyNoInteractions(providerPaymentInfoResponseMapper);
    }

    @Test
    void shouldFindPaymentInfoByCpf() {
        final var cpf = CPF;
        final var paymentResponse = ProviderPaymentInfoResponseFixture.FULL();
        final var payment = PaymentDomainFixture.FULL();

        when(customerClient.getPaymentInfo(cpf))
                .thenReturn(paymentResponse);

        when(providerPaymentInfoResponseMapper.toPaymentDomain(paymentResponse))
                .thenReturn(payment);

        final var result = customerPaymentClientGateway.find(cpf);
        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(payment);

        verify(customerClient).getPaymentInfo(cpf);
        verify(providerPaymentInfoResponseMapper).toPaymentDomain(paymentResponse);
    }

    @Test
    void shouldReturnEmptyWhenPaymentIsNotFound() {
        final var cpf = CPF;

        when(customerClient.getPaymentInfo(cpf))
                .thenThrow(FeignExceptionFixture.NOT_FOUND());

        final var result = customerPaymentClientGateway.find(cpf);
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(customerClient).getPaymentInfo(cpf);
        verifyNoInteractions(providerPaymentInfoResponseMapper);
    }

    @Test
    void shouldReturnFeignExceptionWhenAnyErrorOccurs() {
        final var cpf = CPF;

        when(customerClient.getPaymentInfo(cpf))
                .thenThrow(FeignExceptionFixture.INTERNAL_SERVER_ERROR());

        assertThatThrownBy(() -> customerPaymentClientGateway.find(cpf))
                .isInstanceOf(FeignException.class);

        verify(customerClient).getPaymentInfo(cpf);
        verifyNoInteractions(providerPaymentInfoResponseMapper);
    }
}
