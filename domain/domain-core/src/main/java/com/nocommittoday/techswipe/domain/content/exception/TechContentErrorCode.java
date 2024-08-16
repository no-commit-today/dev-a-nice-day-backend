package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum TechContentErrorCode implements ErrorCodeType {
    PROVIDER_NOT_FOUND("Provider 가 존재하지 않습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CONTENT_NOT_FOUND("Content 가 존재하지 않습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    ;

    private final String message;
    private final int status;

    TechContentErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return "CONTENT-" + name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getStatus() {
        return this.status;
    }
}
