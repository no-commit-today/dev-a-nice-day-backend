package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum AdminErrorCode implements ErrorCodeType {

    // Collection
    COLLECTION_SUMMARY_NOT_REGISTRABLE("등록할 수 없는 요약 형식입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    COLLECTION_COLLECT_FAILURE("컨텐츠 수집에 실패했습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    COLLECTION_ALREADY_COLLECTED("이미 수집된 컨텐츠입니다.", HttpURLConnection.HTTP_CONFLICT),
    COLLECTION_CATEGORY_EDIT_FAILURE("컨텐츠 카테고리 수정에 실패하였습니다.", HttpURLConnection.HTTP_BAD_REQUEST),

    // Content
    TECH_CONTENT_PROVIDER_ICON_EDIT_FAILURE("컨텐츠 제공자 아이콘 수정에 실패하였습니다.", HttpURLConnection.HTTP_INTERNAL_ERROR),
    TECH_CONTENT_PROVIDER_ALREADY_EXISTS("이미 존재하는 컨텐츠 제공자입니다.", HttpURLConnection.HTTP_CONFLICT),
    ;

    private final String message;
    private final int status;

    AdminErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return "ADMIN-" + name();
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
