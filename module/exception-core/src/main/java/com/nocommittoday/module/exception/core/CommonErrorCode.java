package com.nocommittoday.module.exception.core;

public enum CommonErrorCode implements ErrorCodeType {

    UPDATE_FAILURE("COMMON-001", "업데이트에 실패하였습니다.", 500),
    ;

    private final String code;

    private final String message;

    private final int status;

    CommonErrorCode(final String code, final String message, final int status) {
        this.code = code;
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
