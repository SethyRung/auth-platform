package com.sethy.service.common.api_response;

public enum ApiResponseCode {
    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    NOT_FOUND("NOT_FOUND"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    INVALID_REQUEST("INVALID_REQUEST"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String code;

    ApiResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
