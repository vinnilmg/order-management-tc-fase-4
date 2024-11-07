package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.application.dto.ApiErrorResponse;
import com.fiap.techchallenge4.product.application.service.ProcessBatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@AllArgsConstructor
public class BatchController {

    private final ProcessBatchService processBatchService;

    @PostMapping("/process")
    public ResponseEntity<ApiErrorResponse> processBatch() {
        processBatchService.execute();
        return ResponseEntity.ok(new ApiErrorResponse("Batch finished successfully."));
    }

}
