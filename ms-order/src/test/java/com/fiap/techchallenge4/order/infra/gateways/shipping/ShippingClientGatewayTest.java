package com.fiap.techchallenge4.order.infra.gateways.shipping;

import com.fiap.techchallenge4.order.helper.fixture.domain.ShippingDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderShippingResponseFixture;
import com.fiap.techchallenge4.order.infra.client.ShippingClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderShippingResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShippingClientGatewayTest {
    @InjectMocks
    private ShippingClientGateway shippingClientGateway;
    @Mock
    private ShippingClient shippingClient;
    @Mock
    private ProviderShippingResponseMapper providerShippingResponseMapper;

    @Test
    void shouldSimulateShipping() {
        final var postalCode = POSTAL_CODE;
        final var shippingResponse = ProviderShippingResponseFixture.FULL();
        final var expectedShipping = ShippingDomainFixture.FULL();

        when(shippingClient.simulateShipping(postalCode))
                .thenReturn(shippingResponse);

        when(providerShippingResponseMapper.toShippingDomain(shippingResponse))
                .thenReturn(expectedShipping);

        final var result = shippingClientGateway.simulate(postalCode);
        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedShipping);

        verify(shippingClient).simulateShipping(postalCode);
        verify(providerShippingResponseMapper).toShippingDomain(shippingResponse);
    }

    @Test
    void shouldCreateShipping() {
        final var orderId = 1L;
        final var postalCode = POSTAL_CODE;

        doNothing()
                .when(shippingClient)
                .createShipping(any());

        shippingClientGateway.create(orderId, postalCode);

        verify(shippingClient).createShipping(any());
    }
}
