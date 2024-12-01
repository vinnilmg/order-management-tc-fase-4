package com.fiap.techchallenge4.ms_customer.infra.controllers;

import com.fiap.techchallenge4.ms_customer.application.usecases.*;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.AddressResponseMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerRequestMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerResponseMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.PaymentResponseMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.request.CustomerRequest;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.AddressResponse;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.CustomerResponse;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final FindAllCustomersUseCase findAllCustomersUseCase;
    private final FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindPaymentByCustomerCpfUseCase findPaymentByCustomerCpfUseCase;
    private final FindAddressByCustomerCpfUseCase findAddressByCustomerCpfUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final CustomerRequestMapper customerRequestMapper;
    private final CustomerResponseMapper customerResponseMapper;
    private final PaymentResponseMapper paymentResponseMapper;
    private final AddressResponseMapper addressResponseMapper;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase,
                              DeleteCustomerUseCase deleteCustomerUseCase,
                              FindAllCustomersUseCase findAllCustomersUseCase,
                              FindCustomerByCpfUseCase findCustomerByCpfUseCase,
                              FindCustomerByIdUseCase findCustomerByIdUseCase,
                              FindPaymentByCustomerCpfUseCase findPaymentByCustomerCpfUseCase,
                              FindAddressByCustomerCpfUseCase findAddressByCustomerCpfUseCase,
                              UpdateCustomerUseCase updateCustomerUseCase,
                              CustomerRequestMapper customerRequestMapper,
                              CustomerResponseMapper customerResponseMapper,
                              PaymentResponseMapper paymentResponseMapper, AddressResponseMapper addressResponseMapper) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.findAllCustomersUseCase = findAllCustomersUseCase;
        this.findCustomerByCpfUseCase = findCustomerByCpfUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.findPaymentByCustomerCpfUseCase = findPaymentByCustomerCpfUseCase;
        this.findAddressByCustomerCpfUseCase = findAddressByCustomerCpfUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.customerRequestMapper = customerRequestMapper;
        this.customerResponseMapper = customerResponseMapper;
        this.paymentResponseMapper = paymentResponseMapper;
        this.addressResponseMapper = addressResponseMapper;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        final var customers = findAllCustomersUseCase.find();
        return ResponseEntity.ok()
                .body(customerResponseMapper.toResponses(customers));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable final Long id) {
        final var customer = findCustomerByIdUseCase.find(id);
        return ResponseEntity.ok()
                .body(customerResponseMapper.toResponse(customer));
    }

    @GetMapping("cpf/{cpf}")
    public ResponseEntity<CustomerResponse> findCustomerByCpf(@PathVariable final String cpf) {
        final var customer = findCustomerByCpfUseCase.find(cpf);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerResponseMapper.toResponse(customer));
    }

    @GetMapping("paymentInfo/{cpf}")
    public ResponseEntity<PaymentResponse> findPaymentByCustomerCpf(@PathVariable final String cpf) {
        final var payment = findPaymentByCustomerCpfUseCase.find(cpf);
        return ResponseEntity.ok()
                .body(paymentResponseMapper.toResponse(payment));
    }

    @GetMapping("addressInfo/{cpf}")
    public ResponseEntity<AddressResponse> findAddressByCustomerCpf(@PathVariable final String cpf) {
        final var address = findAddressByCustomerCpfUseCase.find(cpf);
        return ResponseEntity.ok()
                .body(addressResponseMapper.toResponse(address));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody final CustomerRequest customerRequest) {
        final var customer = createCustomerUseCase.execute(customerRequestMapper.toCustomer(customerRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerResponseMapper.toResponse(customer));
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable final Long id, @Valid @RequestBody final CustomerRequest customerRequest) {
        updateCustomerUseCase.execute(id, customerRequestMapper.toCustomer(customerRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable final Long id) {
        deleteCustomerUseCase.execute(id);
    }
}
