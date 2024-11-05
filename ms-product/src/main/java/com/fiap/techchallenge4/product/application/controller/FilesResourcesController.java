package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FilesResourcesController {

    private final CsvLoaderService csvLoaderService;

    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CsvLoader>> findFilesToLoad() {

        return new ResponseEntity<>(csvLoaderService.findAllByStatusPending(), HttpStatus.OK);
    }
}