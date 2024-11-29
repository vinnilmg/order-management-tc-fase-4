package com.fiap.techchallenge4.ms_shipping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service", url= "${order-service.url}")
public interface OrderServiceClient {

    @PutMapping("/{orderId}/finish")
    void finishOrder(@PathVariable Long orderId);

    @PutMapping("/{orderId}/shipping")
    void updateOrderToDeliveryRoute(@PathVariable Long orderId);
}
