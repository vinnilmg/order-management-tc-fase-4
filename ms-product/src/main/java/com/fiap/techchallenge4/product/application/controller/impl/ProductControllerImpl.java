package com.fiap.techchallenge4.product.application.controller.impl;

import com.fiap.techchallenge4.product.application.controller.ProductController;
import com.fiap.techchallenge4.product.application.dto.ApiErrorResponse;
import com.fiap.techchallenge4.product.application.dto.ProductQuantityDTO;
import com.fiap.techchallenge4.product.application.service.ProductService;
import com.fiap.techchallenge4.product.core.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    @PutMapping("/{skuId}/decrease-stock")
    public ResponseEntity<ApiErrorResponse> decreaseStock(
            @PathVariable Long skuId,
            @RequestBody ProductQuantityDTO productQuantityDTO) {
        productService.decreaseStock(skuId, productQuantityDTO.getQuantity());
        return ResponseEntity.ok(new ApiErrorResponse("Stock diminuido com Sucesso."));
    }


    @Override
    @PutMapping("/{skuId}/additional-stock")
    public ResponseEntity<ApiErrorResponse> additionalStock(
            @PathVariable Long skuId,
            @RequestBody ProductQuantityDTO productQuantityDTO) {
        productService.addStock(skuId, productQuantityDTO.getQuantity());
        return ResponseEntity.ok(new ApiErrorResponse("Stock adicionado com Sucesso."));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    @Override
    @GetMapping("/skuId/{skuId}")
    public ResponseEntity<Product> findProductBySkuId(
            @PathVariable Long skuId) {
        return ResponseEntity.ok(productService.findBySkuId(skuId));
    }
}
