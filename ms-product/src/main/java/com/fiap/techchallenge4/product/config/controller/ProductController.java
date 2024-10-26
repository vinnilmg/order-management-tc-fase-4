package com.fiap.techchallenge4.product.config.controller;

import com.fiap.techchallenge4.product.service.ProcessBatchService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProcessBatchService processBatchService;

    @PostMapping("/process")
    public ResponseEntity<String> processBatch() {
        processBatchService.execute();
        return ResponseEntity.ok("Batch process started successfully.");
    }
}
