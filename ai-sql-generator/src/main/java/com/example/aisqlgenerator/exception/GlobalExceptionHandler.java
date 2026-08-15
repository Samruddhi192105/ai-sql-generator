package com.example.aisqlgenerator.exception;

import com.example.aisqlgenerator.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import com.example.aisqlgenerator.exception.AIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(AIException.class)
    public ResponseEntity<ApiError> handleAIException(
            AIException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "AI Service Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request",
                "Request body contains invalid JSON",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}