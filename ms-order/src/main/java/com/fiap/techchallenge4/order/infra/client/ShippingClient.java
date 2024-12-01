package com.fiap.techchallenge4.order.infra.client;

import com.fiap.techchallenge4.order.infra.client.request.CreateShippingRequest;
import com.fiap.techchallenge4.order.infra.client.response.ProviderShippingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "shipping", url = "${ms.shipping.host}")
public interface ShippingClient {

    @GetMapping(value = "/api/shippings/{postalCode}", produces = "application/json")
    ProviderShippingResponse simulateShipping(@PathVariable String postalCode);

    @PostMapping(value = "/api/deliveries")
    void createShipping(@RequestBody CreateShippingRequest request);
}
