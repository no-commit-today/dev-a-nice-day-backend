package com.nocommittoday.techswipe.infrastructure.web;

public class ClientException extends RuntimeException {

    public ClientException(Exception cause) {
        super(cause);
    }

    public static class NotFound extends ClientException {
        NotFound(Exception cause) {
            super(cause);
        }
    }

    public static class Unauthorized extends ClientException {
        Unauthorized(Exception cause) {
            super(cause);
        }
    }
}
