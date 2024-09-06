package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum CollectionErrorCode implements ErrorCodeType {
    CATEGORIZE_UNABLE("카테고리화 할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARIZE_UNABLE("요약할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    NOT_FOUND("수집된 컨텐츠를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    PUBLISH_UNABLE("컨텐츠를 발행할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ILLEGAL_CATEGORY("잘못된 카테고리입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    INITIALIZATION_FAILURE("초기화에 실패했습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String message;
    private final int status;

    CollectionErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return "COLLECTION-" + name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
