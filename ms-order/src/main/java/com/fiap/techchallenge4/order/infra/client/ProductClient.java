package com.fiap.techchallenge4.order.infra.client;

import com.fiap.techchallenge4.order.infra.client.request.UpdateProductStockRequest;
import com.fiap.techchallenge4.order.infra.client.response.ProviderProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "product", url = "${ms.product.host}/api/products")
public interface ProductClient {

    @GetMapping(value = "/{sku}", produces = "application/json")
    ProviderProductResponse getProductById(@PathVariable("sku") Long sku);

    @PutMapping(value = "/{sku}/stock/decrease")
    void decreaseStock(@PathVariable("sku") Long sku, @RequestBody UpdateProductStockRequest request);
}