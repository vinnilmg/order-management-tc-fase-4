package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.application.dto.ProductQuantityDTO;
import com.fiap.techchallenge4.product.core.model.Product;
import com.fiap.techchallenge4.product.application.service.ProcessBatchService;
import com.fiap.techchallenge4.product.application.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProcessBatchService processBatchService;

    private final ProductService productService;

    @PostMapping("/process")
    public ResponseEntity<String> processBatch() {
        processBatchService.execute();
        return ResponseEntity.ok("Batch process started successfully.");
    }

    @PutMapping("/{skuId}/decrease-stock")
    public ResponseEntity<String> decreaseStock(
            @PathVariable Long id,
            @RequestBody ProductQuantityDTO productQuantityDTO) {
        productService.decreaseStock(id, productQuantityDTO.getQuantity());
        return ResponseEntity.ok("Stock decreased successfully");
        /*
        try {
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock");
        }

         */
    }


    @PutMapping("/{skuId}/additional-stock")
    public ResponseEntity<String> additionalStock(
            @PathVariable Long id,
            @RequestBody ProductQuantityDTO productQuantityDTO) {
        productService.addStock(id, productQuantityDTO.getQuantity());
        return ResponseEntity.ok("Stock additional successfully");
        /*
        try {
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock");
        }

         */
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/skuId/{skuId}")
    public ResponseEntity<Product> findProductBySkuId(
            @PathVariable Long skuId) {
        return ResponseEntity.ok(productService.findBySkuId(skuId));
    }
}
