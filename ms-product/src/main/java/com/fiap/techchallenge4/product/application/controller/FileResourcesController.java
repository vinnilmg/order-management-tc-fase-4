package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.core.model.CsvLoader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FileResourcesController {
    @Operation(
            summary = "Obtém os arquivos pendentes",
            description = "Retorna uma lista de arquivos com status PENDENTE para carregar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Arquivos encontrados com sucesso",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "Nenhum arquivo encontrado com status PENDENTE")
            }
    )
    ResponseEntity<List<CsvLoader>> findFilesToLoad();
}
