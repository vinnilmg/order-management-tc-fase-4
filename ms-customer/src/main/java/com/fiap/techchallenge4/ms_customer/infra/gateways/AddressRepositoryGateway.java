package com.fiap.techchallenge4.ms_customer.infra.gateways;

import com.fiap.techchallenge4.ms_customer.application.gateways.AddressGateway;
import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.AddressEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.AddressRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddressRepositoryGateway implements AddressGateway {
    private final AddressRepository addressRepository;
    private final AddressEntityMapper addressEntityMapper;

    public AddressRepositoryGateway(AddressRepository addressRepository,
                                    AddressEntityMapper addressEntityMapper) {
        this.addressRepository = addressRepository;
        this.addressEntityMapper = addressEntityMapper;
    }

    @Override
    public Address create(final Address address) {
        final var entity = addressEntityMapper.toEntity(address);
        final var entitySaved = addressRepository.save(entity);
        return addressEntityMapper.toDomain(entitySaved);
    }

    @Override
    public List<Address> findAllAddresses() {
        final var entities = addressRepository.findAll();
        return addressEntityMapper.toDomains(entities);
    }

    @Override
    public Optional<Address> findAddressById(final Long id) {
        return addressRepository.findById(id)
                .map(addressEntityMapper::toDomain);
    }

    @Override
    public void update(final Address address) {
        final var entity = addressEntityMapper.toEntity(address);
        addressRepository.save(entity);
    }

    @Override
    public void delete(final Long id) {
        addressRepository.deleteById(id);
    }
}
