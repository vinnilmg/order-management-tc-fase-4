package com.fiap.techchallenge4.order.infra.client;

import com.fiap.techchallenge4.order.infra.client.response.ProviderProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product", url = "${ms.product.host}")
public interface ProductClient {

    @GetMapping(value = "/product/{sku}", produces = "application/json")
    ProviderProductResponse getProductById(@PathVariable("sku") Long sku);
}
