package com.nocommittoday.client.core;

public class ClientException extends RuntimeException {

    public ClientException(final Exception ex) {
        super(ex);
    }
}
