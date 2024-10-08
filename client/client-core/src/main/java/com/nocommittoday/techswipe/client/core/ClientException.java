package com.nocommittoday.techswipe.client.core;

public class ClientException extends RuntimeException {

    public ClientException() {
    }

    public ClientException(Exception cause) {
        super(cause);
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Exception cause) {
        super(message, cause);
    }
}
