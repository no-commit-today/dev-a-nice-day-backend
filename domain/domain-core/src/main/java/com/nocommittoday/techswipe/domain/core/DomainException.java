package com.nocommittoday.techswipe.domain.core;

import javax.annotation.Nullable;

public class DomainException extends RuntimeException {

    private final ErrorCodeType errorCode;

    @Nullable
    private final Object data;

    public DomainException(ErrorCodeType errorCode) {
        super();
        this.errorCode = errorCode;
        this.data = null;
    }

    public DomainException(ErrorCodeType errorCode, @Nullable Object data) {
        super(errorCode.getMessage() + " data=" + data);
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
