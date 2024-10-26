package com.fiap.techchallenge4.product.config.controller;

import com.fiap.techchallenge4.product.batch.writer.helper.FileWatcherToProcess;
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

    @GetMapping(value = "/files-to-load", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findFilesToLoad() {

        var files = FileWatcherToProcess.getFileNames();

        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
