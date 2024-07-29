package com.nocommittoday.techswipe.client.core;

public class ClientException extends RuntimeException {

    public ClientException() {
    }

    public ClientException(final Exception cause) {
        super(cause);
    }

    public ClientException(final String message) {
        super(message);
    }

    public ClientException(final String message, final Exception cause) {
        super(message, cause);
    }
}
