package com.fintaxpro.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ----------------------------------------------------
    // 1. VALIDATION ERRORS (400)
    // ----------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.warn("Validation failed at path={}", request.getRequestURI());

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // ----------------------------------------------------
    // 1b. TAX RECORD NOT FOUND (404)
    // ----------------------------------------------------
    @ExceptionHandler(TaxRecordNotFoundException.class)
    public ResponseEntity<ApiError> handleTaxRecordNotFound(
            TaxRecordNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Tax record not found at path={} | message={}",
                request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Record Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // ----------------------------------------------------
    // 2. RUNTIME EXCEPTIONS (500)
    // ----------------------------------------------------
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("Runtime exception at path={} | message={}",
                request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ----------------------------------------------------
    // 3. GENERIC FALLBACK HANDLER (500)
    // ----------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception at path={} | message={}",
                request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unhandled Exception",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
