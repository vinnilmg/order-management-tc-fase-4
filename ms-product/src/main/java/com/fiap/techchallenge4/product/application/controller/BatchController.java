package com.fiap.techchallenge4.product.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface BatchController {

    @Operation(
            summary = "Processa arquivos CSV em lote",
            description = "Este endpoint inicia o processamento de arquivos CSV com status 'WAITING' e os move para a pasta de arquivos processados após o carregamento.",
            tags = {"Batch"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processamento do lote concluído com sucesso",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.fiap.techchallenge4.product.application.dto.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum arquivo CSV com status 'WAITING' encontrado para processamento"),
            @ApiResponse(responseCode = "500", description = "Erro ao processar os arquivos CSV")
    })
    ResponseEntity<com.fiap.techchallenge4.product.application.dto.ApiResponse> processBatch();
}
