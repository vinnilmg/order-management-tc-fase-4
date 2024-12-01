package com.fiap.techchallenge4.product.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogError {

    private Long id;

    private String className;

    private Long idEntity;

    private String error;

    private LocalDateTime createdDate;
}
