package com.fiap.techchallenge4.ms_customer.infra.persistence.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.helper.fixture.AddressFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.PaymentFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.CustomerEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.AddressEntity;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.CustomerEntity;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.PaymentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerEntityMapperTest {
    private CustomerEntityMapper mapper;
    private AddressEntityMapper addressEntityMapper;
    private PaymentEntityMapper paymentEntityMapper;

    @BeforeEach
    void init() {
        addressEntityMapper = mock(AddressEntityMapper.class);
        paymentEntityMapper = mock(PaymentEntityMapper.class);
        mapper = new CustomerEntityMapperImpl();
        ReflectionTestUtils.setField(mapper, "addressEntityMapper", addressEntityMapper);
        ReflectionTestUtils.setField(mapper, "paymentEntityMapper", paymentEntityMapper);
    }

    @Test
    void shouldMapCustomerDomain() {
        final var customerEntity = CustomerEntityFixture.CREATED();

        when(addressEntityMapper.toDomain(customerEntity.getAddress()))
                .thenReturn(AddressFixture.NEW_ADDRESS());
        when(paymentEntityMapper.toDomain(customerEntity.getPayment()))
                .thenReturn(PaymentFixture.NEW_PAYMENT());

        try (MockedStatic<Mappers> mockMappers = mockStatic(Mappers.class)) {
            mockMappers.when(() -> Mappers.getMapper(AddressEntityMapper.class))
                    .thenReturn(addressEntityMapper);
            mockMappers.when(() -> Mappers.getMapper(PaymentEntityMapper.class))
                    .thenReturn(paymentEntityMapper);

            final var result = mapper.toDomain(customerEntity);

            assertThat(result)
                    .isNotNull()
                    .extracting(
                            Customer::getId,
                            Customer::getCpf,
                            Customer::getName,
                            Customer::getBirthDate
                    ).containsExactly(
                            customerEntity.getId(),
                            customerEntity.getCpf(),
                            customerEntity.getName(),
                            customerEntity.getBirthDate()
                    );
        }

        verify(addressEntityMapper).toDomain(customerEntity.getAddress());
        verify(paymentEntityMapper).toDomain(customerEntity.getPayment());
    }

    @Test
    void shouldMapCustomerEntity() {
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(addressEntityMapper.toEntity(customer.getAddress()))
                .thenReturn(mock(AddressEntity.class));
        when(paymentEntityMapper.toEntity(customer.getPayment()))
                .thenReturn(mock(PaymentEntity.class));

        try (MockedStatic<Mappers> mockMappers = mockStatic(Mappers.class)) {
            mockMappers.when(() -> Mappers.getMapper(AddressEntityMapper.class))
                    .thenReturn(addressEntityMapper);
            mockMappers.when(() -> Mappers.getMapper(PaymentEntityMapper.class))
                    .thenReturn(paymentEntityMapper);

            final var result = mapper.toEntity(customer);

            assertThat(result)
                    .isNotNull()
                    .extracting(
                            CustomerEntity::getCpf,
                            CustomerEntity::getName,
                            CustomerEntity::getBirthDate
                    ).containsExactly(
                            customer.getCpf(),
                            customer.getName(),
                            customer.getBirthDate()
                    );

            verify(addressEntityMapper).toEntity(customer.getAddress());
            verify(paymentEntityMapper).toEntity(customer.getPayment());
        }
    }


}
