package com.fiap.techchallenge4.order.config.error;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationError(CustomValidationException e, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.of(e.getClass().getSimpleName(), e.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationError(NullPointerException e, WebRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.of(e.getClass().getSimpleName(), e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundError(NotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.of(e.getClass().getSimpleName(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalServerError(Exception e, WebRequest request) {
        log.error("Erro interno: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.of(e.getClass().getSimpleName(), "Internal server error"));
    }
}