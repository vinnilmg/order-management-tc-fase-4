package com.fiap.techchallenge4.order.infra.controllers.response;

public record ShippingResponse(String postalCode, String price, String estimate) {
}
