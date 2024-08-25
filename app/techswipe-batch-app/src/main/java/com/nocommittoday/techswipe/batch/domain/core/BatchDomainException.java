package com.nocommittoday.techswipe.batch.domain.core;

public abstract class BatchDomainException extends RuntimeException {

    public BatchDomainException() {
    }

    public BatchDomainException(Throwable cause) {
        super(cause);
    }

    public BatchDomainException(String message) {
        super(message);
    }

    public BatchDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public BatchDomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
