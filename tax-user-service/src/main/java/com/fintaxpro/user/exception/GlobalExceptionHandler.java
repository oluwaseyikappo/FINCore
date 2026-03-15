/*
package com.fintaxpro.user.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(fieldError ->
                        getFieldOrder(fieldError.getField())))
                .map(fieldError ->
                        new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        return ResponseEntity.internalServerError().body(
                Map.of(
                        "error", "Internal Server Error",
                        "message", ex.getMessage()
                )
        );
    }
    private int getFieldOrder(String fieldName) {
        List<String> order = List.of("firstName", "lastName", "email", "password");
        return order.indexOf(fieldName);
    }
}
*/