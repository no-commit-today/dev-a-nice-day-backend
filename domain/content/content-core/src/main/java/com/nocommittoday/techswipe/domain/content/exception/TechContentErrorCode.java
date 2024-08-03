package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum TechContentErrorCode implements ErrorCodeType {
    PROVIDER_NOT_FOUND("001", "Provider 가 존재하지 않습니다.",
            HttpURLConnection.HTTP_BAD_REQUEST),
    PROVIDER_URL_EXISTS("002", "Provider URL 이 이미 존재합니다.",
            HttpURLConnection.HTTP_CONFLICT),
    CONTENT_NOT_FOUND("003", "Content 가 존재하지 않습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final int status;

    TechContentErrorCode(String code, String message, int status) {
        this.code = "CONTENT-" + code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return this.code;
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
