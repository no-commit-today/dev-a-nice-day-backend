package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum AdminErrorCode implements ErrorCodeType {

    COLLECTION_SUMMARY_NOT_REGISTRABLE("001", "등록할 수 없는 요약 형식입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;

    AdminErrorCode(String code, String message, int status) {
        this.code = "ADMIN-" + code;
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
