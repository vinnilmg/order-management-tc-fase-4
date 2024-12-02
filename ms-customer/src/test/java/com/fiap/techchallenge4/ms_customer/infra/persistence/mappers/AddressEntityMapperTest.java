package com.fiap.techchallenge4.ms_customer.infra.persistence.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.helper.fixture.AddressFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.AddressEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.AddressEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AddressEntityMapperTest {
    private AddressEntityMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new AddressEntityMapperImpl();
    }

    @Test
    void shouldMapAddressDomain() {
        final var addressEntity = AddressEntityFixture.CREATED();
        final var result = mapper.toDomain(addressEntity);

        assertThat(result)
                .isNotNull()
                .extracting(
                        Address::getId,
                        Address::getStreet,
                        Address::getNumber,
                        Address::getComplement,
                        Address::getDistrict,
                        Address::getCity,
                        Address::getState,
                        Address::getCep
                ).containsExactly(
                        addressEntity.getId(),
                        addressEntity.getStreet(),
                        addressEntity.getNumber(),
                        addressEntity.getComplement(),
                        addressEntity.getDistrict(),
                        addressEntity.getCity(),
                        addressEntity.getState(),
                        addressEntity.getCep()
                );
    }

    @Test
    void shouldMapAddressDomains() {
        final var addressEntities = AddressEntityFixture.ADDRESSES();
        final var result = mapper.toDomains(addressEntities);

        assertThat(result)
                .isNotEmpty()
                .hasSize(addressEntities.size());
    }

    @Test
    void shouldMapAddressEntity() {
        final var address = AddressFixture.NEW_ADDRESS();
        final var result = mapper.toEntity(address);

        assertThat(result)
                .isNotNull()
                .extracting(
                        AddressEntity::getStreet,
                        AddressEntity::getNumber,
                        AddressEntity::getComplement,
                        AddressEntity::getDistrict,
                        AddressEntity::getCity,
                        AddressEntity::getState,
                        AddressEntity::getCep
                ).containsExactly(
                        address.getStreet(),
                        address.getNumber(),
                        address.getComplement(),
                        address.getDistrict(),
                        address.getCity(),
                        address.getState(),
                        address.getCep()
                );
    }

}
