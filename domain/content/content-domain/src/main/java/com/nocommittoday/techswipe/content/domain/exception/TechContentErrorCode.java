package com.nocommittoday.techswipe.content.domain.exception;

import com.nocommittoday.techswipe.core.domain.ErrorCodeType;
import lombok.Getter;

import java.net.HttpURLConnection;

@Getter
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
}
