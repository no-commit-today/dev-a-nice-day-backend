package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.core.domain.exception.ErrorCodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.HttpURLConnection;

@Getter
@RequiredArgsConstructor
public enum TechContentErrorCode implements ErrorCodeType {
    PROVIDER_NOT_FOUND("CONTENT-001", "Provider 가 존재하지 않습니다.",
            HttpURLConnection.HTTP_BAD_REQUEST),
    PROVIDER_URL_EXISTS("CONTENT-002", "Provider URL 이 이미 존재합니다.",
            HttpURLConnection.HTTP_CONFLICT),
    ;

    private final String code;
    private final String message;
    private final int status;
}
