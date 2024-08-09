package com.nocommittoday.techswipe.domain.subscription.exception;

public abstract class CollectionInfrastructureWebException extends RuntimeException {

    public CollectionInfrastructureWebException() {
    }

    public CollectionInfrastructureWebException(Exception cause) {
        super(cause);
    }

    public CollectionInfrastructureWebException(String message) {
        super(message);
    }

    public CollectionInfrastructureWebException(String message, Exception cause) {
        super(message, cause);
    }

}