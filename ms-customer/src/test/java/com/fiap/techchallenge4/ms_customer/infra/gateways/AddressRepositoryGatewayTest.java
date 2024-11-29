package com.fiap.techchallenge4.ms_customer.infra.gateways;

import com.fiap.techchallenge4.ms_customer.helper.fixture.AddressFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.AddressEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.AddressEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.DEFAULT_CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressRepositoryGatewayTest {
    @InjectMocks
    private AddressRepositoryGateway addressRepositoryGateway;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressEntityMapper addressEntityMapper;

    @Test
    void shouldFindAllAddresses() {
        final var addresses = List.of(AddressEntityFixture.CREATED());
        final var expectedAddresses = AddressFixture.ADDRESSES();

        when(addressRepository.findAll())
                .thenReturn(addresses);

        when(addressEntityMapper.toDomains(addresses))
                .thenReturn(expectedAddresses);

        final var result = addressRepositoryGateway.findAllAddresses();

        assertThat(result)
                .isNotEmpty()
                .hasSize(addresses.size())
                .isEqualTo(expectedAddresses);

        verify(addressRepository).findAll();
        verify(addressEntityMapper).toDomains(addresses);
    }

    @Test
    void shouldFindAddressById() {
        final var addressId = 1L;
        final var addressEntity = AddressEntityFixture.CREATED();
        final var expectedAddress = AddressFixture.NEW_ADDRESS();

        when(addressRepository.findById(addressId))
                .thenReturn(Optional.of(addressEntity));

        when(addressEntityMapper.toDomain(addressEntity))
                .thenReturn(expectedAddress);

        final var result = addressRepositoryGateway.findAddressById(addressId);

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedAddress);

        verify(addressRepository).findById(addressId);
        verify(addressEntityMapper).toDomain(addressEntity);
    }

    @Test
    void shouldFindAddressByCustomerCpf() {
        final var cpf = DEFAULT_CPF;
        final var addressEntity = AddressEntityFixture.CREATED();
        final var expectedAddress = AddressFixture.NEW_ADDRESS();

        when(addressRepository.findAddressByCustomerCpf(cpf))
                .thenReturn(Optional.of(addressEntity));

        when(addressEntityMapper.toDomain(addressEntity))
                .thenReturn(expectedAddress);

        final var result = addressRepositoryGateway.findAddressByCustomerCpf(cpf);

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedAddress);

        verify(addressRepository).findAddressByCustomerCpf(cpf);
        verify(addressEntityMapper).toDomain(addressEntity);
    }

    @Test
    void shouldCreateAddress() {
        final var address = AddressFixture.NEW_ADDRESS();
        final var addressEntity = AddressEntityFixture.CREATED();

        when(addressEntityMapper.toEntity(address))
                .thenReturn(addressEntity);

        when(addressRepository.save(addressEntity))
                .thenReturn(addressEntity);

        when(addressEntityMapper.toDomain(addressEntity))
                .thenReturn(address);

        final var result = addressRepositoryGateway.create(address);

        assertThat(result)
                .isNotNull()
                .isEqualTo(address);

        verify(addressEntityMapper).toEntity(address);
        verify(addressRepository).save(addressEntity);
        verify(addressEntityMapper).toDomain(addressEntity);
    }

    @Test
    void shouldUpdateAddress() {
        final var address = AddressFixture.NEW_ADDRESS();
        final var addressEntity = AddressEntityFixture.CREATED();

        when(addressEntityMapper.toEntity(address))
                .thenReturn(addressEntity);

        addressRepositoryGateway.update(address);

        verify(addressEntityMapper).toEntity(address);
        verify(addressRepository).save(addressEntity);
    }

    @Test
    void shouldDeleteAddress() {
        final var addressId = 1L;

        addressRepositoryGateway.delete(addressId);

        verify(addressRepository).deleteById(addressId);
    }
}
