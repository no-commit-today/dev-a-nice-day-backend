package com.nocommittoday.techswipe.domain.subscription.exception;

public class SubscriptionApplicationException extends RuntimeException {

    public SubscriptionApplicationException() {
    }

    public SubscriptionApplicationException(Exception cause) {
        super(cause);
    }

    public SubscriptionApplicationException(String message) {
        super(message);
    }

    public SubscriptionApplicationException(String message, Exception cause) {
        super(message, cause);
    }

    public SubscriptionApplicationException(
            String message, Exception cause,
            boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
