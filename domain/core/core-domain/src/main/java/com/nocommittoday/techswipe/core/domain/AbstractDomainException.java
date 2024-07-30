package com.nocommittoday.techswipe.core.domain;

public abstract class AbstractDomainException extends RuntimeException {

    private final ErrorCodeType errorCode;

    protected AbstractDomainException(ErrorCodeType errorCode) {
        super();
        this.errorCode = errorCode;
    }

    protected AbstractDomainException(ErrorCodeType errorCode, String message) {
        super(errorCode.getMessage() + " >> " + message);
        this.errorCode = errorCode;
    }

    protected AbstractDomainException(ErrorCodeType errorCode, String message, Exception cause) {
        super(errorCode.getMessage() + " >> " + message, cause);
        this.errorCode = errorCode;
    }

    protected AbstractDomainException(ErrorCodeType errorCode, Exception cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCodeType getErrorCode() {
        return errorCode;
    }
}
