package com.fiap.techchallenge4.order.domain.entities.customer.address;

import static java.util.Objects.requireNonNull;

public class AddressDomain implements Address {
    private final Long id;
    private final String street;
    private final Integer number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String postalCode;

    public AddressDomain(
            final Long id,
            final String street,
            final Integer number,
            final String complement,
            final String neighborhood,
            final String city,
            final String state,
            final String postalCode
    ) {
        this.id = requireNonNull(id, "Address Id cannot be null");
        this.street = requireNonNull(street, "Address Street cannot be null");
        this.number = requireNonNull(number, "Address Number cannot be null");
        this.complement = complement;
        this.neighborhood = requireNonNull(neighborhood, "Address Neighborhood cannot be null");
        this.city = requireNonNull(city, "Address City cannot be null");
        this.state = requireNonNull(state, "Address State cannot be null");
        this.postalCode = requireNonNull(postalCode, "Address Postal Code cannot be null");
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public String getComplement() {
        return complement;
    }

    @Override
    public String getNeighborhood() {
        return neighborhood;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }
}
