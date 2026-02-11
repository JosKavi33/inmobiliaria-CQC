package com.jose.inmobiliaria.property.service.api.exception;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private int status;
    private String error;
    private Object message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiErrorResponse(int status, String error, Object message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
