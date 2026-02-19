package com.jose.inmobiliaria.property.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ===============================
   403 - Acceso denegado
=============================== */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {

        ApiErrorResponse error = new ApiErrorResponse(
                403,
                "ACCESS_DENIED",
                "You do not have permission to perform this action",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


    /* ===============================
       404 - Recurso no encontrado
    =============================== */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {

        ApiErrorResponse error = new ApiErrorResponse(
                404,
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /* ===============================
       400 - Enum inválido / query param mal tipeado
    =============================== */
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            ConversionFailedException.class
    })
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            Exception ex,
            HttpServletRequest request
    ) {

        ApiErrorResponse error = new ApiErrorResponse(
                400,
                "INVALID_PARAMETER",
                "Invalid value for request parameter",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    /* ===============================
       400 - JSON mal formado
    =============================== */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {

        ApiErrorResponse error = new ApiErrorResponse(
                400,
                "INVALID_REQUEST_BODY",
                "Request body is invalid or malformed",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    /* ===============================
       400 - Validaciones (@Valid)
    =============================== */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                400,
                "VALIDATION_ERROR",
                errors,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /* ===============================
       400 - Argumentos inválidos manuales
    =============================== */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {

        ApiErrorResponse error = new ApiErrorResponse(
                400,
                "INVALID_ARGUMENT",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    /* ===============================
       500 - Error no controlado
    =============================== */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {

        ApiErrorResponse error = new ApiErrorResponse(
                500,
                "INTERNAL_SERVER_ERROR",
                "Unexpected error occurred",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
