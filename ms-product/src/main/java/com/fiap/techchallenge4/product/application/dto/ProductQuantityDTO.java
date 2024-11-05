package com.fiap.techchallenge4.product.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductQuantityDTO {

    @JsonProperty("quantity")
    @NotNull(message = "quantity is required.")
    private Integer quantity;
}
