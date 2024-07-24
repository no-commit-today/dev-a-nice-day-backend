package com.nocommittoday.techswipe.subscription.service.exception;

public class SubscriptionApplicationException extends RuntimeException {

    public SubscriptionApplicationException() {
    }

    public SubscriptionApplicationException(final Exception cause) {
        super(cause);
    }

    public SubscriptionApplicationException(final String message) {
        super(message);
    }

    public SubscriptionApplicationException(final String message, final Exception cause) {
        super(message, cause);
    }

    public SubscriptionApplicationException(
            final String message, final Exception cause,
            final boolean enableSuppression, final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
