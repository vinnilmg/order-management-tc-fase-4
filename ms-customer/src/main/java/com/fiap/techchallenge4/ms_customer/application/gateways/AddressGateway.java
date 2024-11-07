package com.fiap.techchallenge4.ms_customer.application.gateways;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import java.util.List;
import java.util.Optional;

public interface AddressGateway {
    Address create(Address address);

    List<Address> findAllAddresses();

    Optional<Address> findAddressById(Long id);

    void update(Address address);

    void delete(Long id);

}
