package com.nocommittoday.techswipe.domain.core;

import javax.annotation.Nullable;

public class DomainException extends RuntimeException {

    private final ErrorCodeType errorCode;

    @Nullable
    private final Object data;

    public DomainException(ErrorCodeType errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = null;
    }

    public DomainException(ErrorCodeType errorCode, @Nullable Object data) {
        super(errorCode.getMessage() + ">> data=" + data);
        this.errorCode = errorCode;
        this.data = data;
    }

    public DomainException(ErrorCodeType errorCode, String message) {
        super(errorCode.getMessage() + " >> " + message);
        this.errorCode = errorCode;
        this.data = null;
    }

    public DomainException(ErrorCodeType errorCode, String message, @Nullable Object data) {
        super(errorCode.getMessage() + " >> " + message + " data=" + data);
        this.errorCode = errorCode;
        this.data = data;
    }

    public DomainException(ErrorCodeType errorCode, String message, Exception cause) {
        super(errorCode.getMessage() + " >> " + message, cause);
        this.errorCode = errorCode;
        this.data = null;
    }

    public DomainException(ErrorCodeType errorCode, Exception cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.data = null;
    }

    public ErrorCodeType getErrorCode() {
        return errorCode;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
