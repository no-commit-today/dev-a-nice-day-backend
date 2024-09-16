package com.nocommittoday.techswipe.domain.core;

import javax.annotation.Nullable;

public abstract class AbstractDomainException extends RuntimeException {

    private final ErrorCodeType errorCode;

    @Nullable
    private final Object data;

    protected AbstractDomainException(ErrorCodeType errorCode, @Nullable Object data) {
        super(errorCode.getMessage() + " >> " + data);
        this.errorCode = errorCode;
        this.data = data;
    }

    protected AbstractDomainException(
            ErrorCodeType errorCode, Exception cause, @Nullable Object data
    ) {
        super(errorCode.getMessage() + " >> " + data, cause);
        this.errorCode = errorCode;
        this.data = data;
    }

    public ErrorCodeType getErrorCode() {
        return errorCode;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
