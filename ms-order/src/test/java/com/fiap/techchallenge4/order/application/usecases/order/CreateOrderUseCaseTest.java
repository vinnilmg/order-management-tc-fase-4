package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.customer.FindCustomerByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.product.DecreaseStockGateway;
import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.SimulateShippingGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.CreateOrderUseCaseImpl;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.order.helper.fixture.domain.CustomerDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.ProductDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.ShippingDomainFixture;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {
    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;
    @Mock
    private OrderRepositoryGateway createOrderDatabaseGateway;
    @Mock
    private FindProductGateway findProductGateway;
    @Mock
    private CreateOrderKafkaRepositoryGateway createOrderKafkaGateway;
    @Mock
    private DecreaseStockGateway decreaseStockGateway;
    @Mock
    private FindCustomerByCpfGateway findCustomerByCpfGateway;
    @Mock
    private SimulateShippingGateway simulateShippingGateway;

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerNotFound() {
        final var order = OrderDomainFixture.CRIADO();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(order))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");

        verify(findCustomerByCpfGateway).find(order.getCpf());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenProductNotFound() {
        final var order = OrderDomainFixture.CRIADO();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(CustomerDomainFixture.FULL()));

        when(findProductGateway.find(order.getProducts().get(0).getSku()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(order))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product not found");

        verify(findCustomerByCpfGateway).find(order.getCpf());
        verify(findProductGateway).find(order.getProducts().get(0).getSku());
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenProductWithoutStock() {
        final var order = OrderDomainFixture.CRIADO_WITH_TWO_QUANTITY_PRODUCT();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(CustomerDomainFixture.FULL()));

        when(findProductGateway.find(order.getProducts().get(0).getSku()))
                .thenReturn(Optional.of(ProductDomainFixture.WITH_ONE_QUANTITY()));

        assertThatThrownBy(() -> createOrderUseCase.execute(order))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Product without stock");

        verify(findCustomerByCpfGateway).find(order.getCpf());
        verify(findProductGateway).find(order.getProducts().get(0).getSku());
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenProductContainDifferentValue() {
        final var order = OrderDomainFixture.CRIADO();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(CustomerDomainFixture.FULL()));

        when(findProductGateway.find(order.getProducts().get(0).getSku()))
                .thenReturn(Optional.of(ProductDomainFixture.SKU_3001()));

        assertThatThrownBy(() -> createOrderUseCase.execute(order))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Product contain different value");

        verify(findCustomerByCpfGateway).find(order.getCpf());
        verify(findProductGateway).find(order.getProducts().get(0).getSku());
    }

    @Test
    void shouldCreateOrder() {
        final var order = OrderDomainFixture.CRIADO();
        final var product = order.getProducts().get(0);
        final var customer = CustomerDomainFixture.FULL();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(customer));

        when(findProductGateway.find(product.getSku()))
                .thenReturn(Optional.of(ProductDomainFixture.FULL()));

        doNothing().
                when(decreaseStockGateway)
                .decrease(product.getSku(), product.getQuantity());

        when(simulateShippingGateway.simulate(customer.getAddress().getPostalCode()))
                .thenReturn(ShippingDomainFixture.FULL());

        when(createOrderDatabaseGateway.create(order))
                .thenReturn(order);

        when(createOrderKafkaGateway.create(order))
                .thenReturn(order);

        final var result = createOrderUseCase.execute(order);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(findCustomerByCpfGateway).find(order.getCpf());
        verify(findProductGateway).find(product.getSku());
        verify(decreaseStockGateway).decrease(product.getSku(), product.getQuantity());
        verify(simulateShippingGateway).simulate(customer.getAddress().getPostalCode());
        verify(createOrderDatabaseGateway).create(order);
        verify(createOrderKafkaGateway).create(order);
    }
}