package com.nocommittoday.techswipe.subscription.infrastructure.exception;

public abstract class CollectionInfrastructureWebException extends RuntimeException {

    public CollectionInfrastructureWebException() {
    }

    public CollectionInfrastructureWebException(final Exception cause) {
        super(cause);
    }

    public CollectionInfrastructureWebException(final String message) {
        super(message);
    }

    public CollectionInfrastructureWebException(final String message, final Exception cause) {
        super(message, cause);
    }

}
