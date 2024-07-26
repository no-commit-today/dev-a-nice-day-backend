package com.nocommittoday.techswipe.image.service.exception;

public class ImageApplicationException extends RuntimeException {

    public ImageApplicationException() {
    }

    public ImageApplicationException(final Exception cause) {
        super(cause);
    }

    public ImageApplicationException(final String message) {
        super(message);
    }

    public ImageApplicationException(final String message, final Exception cause) {
        super(message, cause);
    }
}
