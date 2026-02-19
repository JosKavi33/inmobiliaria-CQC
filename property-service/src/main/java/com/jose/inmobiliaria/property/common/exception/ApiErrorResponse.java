package com.jose.inmobiliaria.property.common.exception;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private final int status;
    private final String error;
    private final Object message;
    private final LocalDateTime timestamp;
    private final String path;

    public ApiErrorResponse(int status, String error, Object message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
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

    public String getPath() {
        return path;
    }
}
