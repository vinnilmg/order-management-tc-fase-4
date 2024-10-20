package com.fiap.techchallenge4.ms_customer.domain.entities.address;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;

import static java.util.Objects.isNull;

public class AddressDomain implements Address {
    private Long id;
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String cep;

    public static Address of(
            final Long id,
            final String street,
            final String number,
            final String complement,
            final String district,
            final String city,
            final String state,
            final String cep
    ) {
        return new AddressDomain(
                id,
                street,
                number,
                complement,
                district,
                city,
                state,
                cep
        );
    }

    public AddressDomain(
            final String street,
            final String number,
            final String complement,
            final String district,
            final String city,
            final String state,
            final String cep
    ) {
        this.street = streetValidation(street);
        this.number = numberValidation(number);
        this.complement = complement;
        this.district = districtValidation(district);
        this.city = cityValidation(city);
        this.state = stateValidation(state);
        this.cep = cepValidation(cep);
    }

    private AddressDomain(
            final Long id,
            final String street,
            final String number,
            final String complement,
            final String district,
            final String city,
            final String state,
            final String cep
    ) {
        this.id = idValidation(id);
        this.street = streetValidation(street);
        this.number = numberValidation(number);
        this.complement = complement;
        this.district = districtValidation(district);
        this.city = cityValidation(city);
        this.state = stateValidation(state);
        this.cep = cepValidation(cep);
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
    public String getNumber() {
        return number;
    }

    @Override
    public String getComplement() {
        return complement;
    }

    @Override
    public String getDistrict() {
        return district;
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
    public String getCep() {
        return cep;
    }

    private static Long idValidation(final Long id) {
        if (isNull(id)) throw CustomValidationException.of("Address Id", "cannot be null");
        if (id < 0) throw CustomValidationException.of("Address Id", "cannot be negative");
        return id;
    }

    private static String streetValidation(final String street) {
        if (isNull(street)) throw CustomValidationException.of("Address Street", "cannot be null");
        return street;
    }

    private static String numberValidation(final String number) {
        if (isNull(number)) throw CustomValidationException.of("Address Number", "cannot be null");
        return number;
    }

    private static String districtValidation(final String district) {
        if (isNull(district)) throw CustomValidationException.of("Address District", "cannot be null");
        return district;
    }

    private static String cityValidation(final String city) {
        if (isNull(city)) throw CustomValidationException.of("Address City", "cannot be null");
        return city;
    }

    private static String stateValidation(final String state) {
        if (isNull(state)) throw CustomValidationException.of("Address State", "cannot be null");
        return state;
    }

    private static String cepValidation(final String cep) {
        if (isNull(cep)) throw CustomValidationException.of("Address CEP", "cannot be null");
        if (cep.length() != 8) throw CustomValidationException.of("Address CEP", "must be 8 positions");
        return cep;
    }

}
