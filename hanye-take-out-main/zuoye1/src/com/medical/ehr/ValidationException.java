package com.medical.ehr;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}