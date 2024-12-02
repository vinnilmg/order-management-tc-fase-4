package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.application.dto.ApiResponse;
import com.fiap.techchallenge4.product.application.dto.ProductQuantityDTO;
import com.fiap.techchallenge4.product.core.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Produto", description = "API de Produto")
public interface ProductController {

    @Operation(summary = "Diminuir o estoque de um produto", description = "Diminui o estoque de um produto por uma quantidade especificada.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Estoque diminuído com sucesso", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Estoque insuficiente", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{skuId}/diminuir-estoque")
    ResponseEntity<Void> decreaseStock(
            @Parameter(description = "ID SKU do produto", required = true) @PathVariable Long skuId,
            @RequestBody ProductQuantityDTO productQuantityDTO
    );

    @Operation(summary = "Adicionar estoque a um produto", description = "Aumenta o estoque de um produto por uma quantidade especificada.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Estoque adicionado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Quantidade inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
    })
    @PutMapping("/{skuId}/adicionar-estoque")
    ResponseEntity<Void> additionalStock(
            @Parameter(description = "ID SKU do produto", required = true) @PathVariable Long skuId,
            @RequestBody ProductQuantityDTO productQuantityDTO
    );

    @Operation(summary = "Buscar produto por ID", description = "Recupera um produto pelo seu ID único.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<Product> findProductById(
            @Parameter(description = "ID do produto", required = true) @PathVariable Long id
    );

    @Operation(summary = "Buscar produto por SKU ID", description = "Recupera um produto pelo seu ID SKU.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/skuId/{skuId}")
    ResponseEntity<Product> findProductBySkuId(
            @Parameter(description = "ID SKU do produto", required = true) @PathVariable Long skuId
    );
}
