package com.fiap.techchallenge4.ms_customer.domain.entities.customer;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

public class CustomerDomain implements Customer {
    private Long id;
    private String cpf;
    private String name;
    private Address address;
    private LocalDate birthDate;
    private Payment payment;

    public static Customer of(
            final Long id,
            final String cpf,
            final String name,
            final Address address,
            final LocalDate birthDate,
            final Payment payment
    ) {
        return new CustomerDomain(
                id,
                cpf,
                name,
                address,
                birthDate,
                payment
        );
    }

    public CustomerDomain(
            final String cpf,
            final String name,
            final Address address,
            final String birthDate,
            final Payment payment
    ) {
        this.cpf = cpfValidation(cpf);
        this.name = nameValidation(name);
        this.address = addressValidation(address);
        this.birthDate = birthDateValidation(LocalDate.parse(birthDate));
        this.payment = paymentValidation(payment);
    }

    private CustomerDomain(
            final Long id,
            final String cpf,
            final String name,
            final Address address,
            final LocalDate birthDate,
            final Payment payment
    ) {
        this.id = idValidation(id);
        this.cpf = cpfValidation(cpf);
        this.name = nameValidation(name);
        this.address = addressValidation(address);
        this.birthDate = birthDateValidation(birthDate);
        this.payment = paymentValidation(payment);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public Payment getPayment() {
        return payment;
    }

    @Override
    public void setId(Long customerId) {
        this.id = customerId;
    }

    private static Long idValidation(final Long id) {
        if (isNull(id)) throw CustomValidationException.of("Customer Id", "cannot be null");
        if (id < 0) throw CustomValidationException.of("Customer Id", "cannot be negative");
        return id;
    }

    private static String cpfValidation(final String cpf) {
        if (isNull(cpf)) throw CustomValidationException.of("Customer CPF", "cannot be null");
        if (cpf.length() != 11) throw CustomValidationException.of("Customer CPF", "must be 11 positions");
        return cpf;
    }

    private static String nameValidation(final String name) {
        if (isNull(name)) throw CustomValidationException.of("Customer Name", "cannot be null");
        return name;
    }

    private static Address addressValidation(final Address address) {
        if (isNull(address)) throw CustomValidationException.of("Customer Address", "cannot be null");
        return address;
    }

    private static LocalDate birthDateValidation(final LocalDate birthDate) {
        if (isNull(birthDate)) throw CustomValidationException.of("Customer Birth Date", "cannot be null");
        LocalDate currentDate = LocalDate.now();
        boolean isOfLegalAge = Period.between(birthDate, currentDate).getYears() >= 18;
        if (!isOfLegalAge) throw
                CustomValidationException.of("Customer Birth Date", "must be at least 18 years ago");
        return birthDate;
    }

    private static Payment paymentValidation(final Payment payment) {
        if (isNull(payment)) throw CustomValidationException.of("Customer Payment", "cannot be null");
        return payment;
    }
}
