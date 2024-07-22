package com.nocommittoday.techswipe.client.core;

public class ClientException extends RuntimeException {

    public ClientException(final Exception ex) {
        super(ex);
    }
}
