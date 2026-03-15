package com.fintaxpro.exception;

public class TaxRecordNotFoundException extends RuntimeException {
    public TaxRecordNotFoundException(String message) {
        super(message);
    }
}
