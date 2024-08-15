package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

public enum AdminErrorCode implements ErrorCodeType {
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
