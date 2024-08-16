package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum CollectionErrorCode implements ErrorCodeType {
    CATEGORIZE_UNABLE("카테고리화 할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARIZE_UNABLE("요약할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    NOT_FOUND("수집된 컨텐츠를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    PUBLISH_UNABLE("컨텐츠를 발행할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CATEGORY_NOT_EDITABLE("카테고리를 수정할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CATEGORY_NOT_APPLICABLE("카테고리를 컨텐츠에 반영할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ILLEGAL_PROVIDER_ID("잘못된 컨텐츠 제공자 아이디입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ALREADY_COLLECTED("이미 수집된 컨텐츠입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
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
