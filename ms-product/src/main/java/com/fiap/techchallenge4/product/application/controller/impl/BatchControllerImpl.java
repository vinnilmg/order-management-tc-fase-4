package com.fiap.techchallenge4.product.application.controller.impl;

import com.fiap.techchallenge4.product.application.controller.BatchController;
import com.fiap.techchallenge4.product.application.dto.ApiResponse;
import com.fiap.techchallenge4.product.application.service.ProcessBatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@AllArgsConstructor
public class BatchControllerImpl implements BatchController {

    private final ProcessBatchService processBatchService;

    @Override
    @PostMapping("/process")
    public ResponseEntity<ApiResponse> processBatch() {
        processBatchService.execute();
        return ResponseEntity.ok(new ApiResponse("Batch finalizado com sucesso."));
    }

}
