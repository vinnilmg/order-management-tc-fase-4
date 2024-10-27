package com.fiap.techchallenge4.order.infra.client;

import com.fiap.techchallenge4.order.infra.client.request.SimulateShippingRequest;
import com.fiap.techchallenge4.order.infra.client.response.ProviderShippingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "shipping", url = "${ms.shipping.host}/api/shippings")
public interface ShippingClient {

    @PostMapping(value = "/simulate", produces = "application/json")
    ProviderShippingResponse simulateShipping(@RequestBody SimulateShippingRequest request);
}
