package com.nocommittoday.techswipe.core.domain.exception;

public abstract class AbstractDomainException extends RuntimeException {

    private final ErrorCodeType errorCode;

    protected AbstractDomainException(final ErrorCodeType errorCode) {
        super();
        this.errorCode = errorCode;
    }

    protected AbstractDomainException(final ErrorCodeType errorCode, final String message) {
        super(errorCode.getMessage() + " >> " + message);
        this.errorCode = errorCode;
    }

    protected AbstractDomainException(final ErrorCodeType errorCode, final String message, final Throwable cause) {
        super(errorCode.getMessage() + " >> " + message, cause);
        this.errorCode = errorCode;
    }

    protected AbstractDomainException(final ErrorCodeType errorCode, final Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCodeType getErrorCode() {
        return errorCode;
    }
}
