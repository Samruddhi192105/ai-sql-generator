package com.example.aisqlgenerator.exception;

public class ValidationException extends IllegalArgumentException {

    public ValidationException(String message) {
        super(message);
    }
}