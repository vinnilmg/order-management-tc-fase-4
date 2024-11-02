package com.fiap.techchallenge4.product.controller;

import com.fiap.techchallenge4.product.service.ProcessBatchService;
import com.fiap.techchallenge4.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/{id}/decrease-stock")
    public ResponseEntity<String> decreaseStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        productService.decreaseStock(id, quantity);
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
}
