package com.fiap.techchallenge4.ms_shipping.config.exception;

import com.fiap.techchallenge4.ms_shipping.dto.error.DefaultErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundExpection.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundExpection ex) {
        ErrorResponse errorResponse = new DefaultErrorResponse(ex.getMessage(), ex.getResource());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
