package com.jose.inmobiliaria.property.service.api.dto.response;

public class ApiResponse<T> {

    private boolean success;
    private String code;
    private T data;
    private Object meta;

    public ApiResponse(boolean success, String code, T data, Object meta) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.meta = meta;
    }

    public ApiResponse(boolean success, String code, T data) {
        this(success, code, data, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public Object getMeta() {
        return meta;
    }
}
