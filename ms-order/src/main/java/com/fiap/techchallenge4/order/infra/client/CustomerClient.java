package com.fiap.techchallenge4.order.infra.client;

import com.fiap.techchallenge4.order.infra.client.response.ProviderCustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customer", url = "${ms.customer.host}/api/customers")
public interface CustomerClient {

    @GetMapping(value = "/{cpf}", produces = "application/json")
    ProviderCustomerResponse getCustomerByCpf(@PathVariable("cpf") String cpf);
}
