package com.fiap.techchallenge4.ms_shipping.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryRequestDto {

    private Long orderId;
    private String postalCode;
}
