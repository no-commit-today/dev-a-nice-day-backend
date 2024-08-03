package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum CollectionErrorCode implements ErrorCodeType {
    CATEGORIZE_UNABLE("001", "카테고리화 할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARIZE_UNABLE("002", "요약할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    NOT_FOUND("003", "수집된 컨텐츠를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    PUBLISH_UNABLE("006", "컨텐츠를 발행할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CATEGORY_NOT_EDITABLE("007", "카테고리를 수정할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CATEGORY_NOT_APPLICABLE("008", "카테고리를 컨텐츠에 반영할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARY_FORMAT_NOT_REGISTRABLE("009", "등록할 수 없는 요약 형식입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ILLEGAL_PROVIDER_ID("010", "잘못된 컨텐츠 제공자 아이디입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ALREADY_COLLECTED("011", "이미 수집된 컨텐츠입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;

    CollectionErrorCode(String code, String message, int status) {
        this.code = "COLLECTION-" + code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return code;
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
