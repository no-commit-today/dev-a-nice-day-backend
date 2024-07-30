package com.nocommittoday.techswipe.image.service.exception;

public class ImageApplicationException extends RuntimeException {

    public ImageApplicationException() {
    }

    public ImageApplicationException(Exception cause) {
        super(cause);
    }

    public ImageApplicationException(String message) {
        super(message);
    }

    public ImageApplicationException(String message, Exception cause) {
        super(message, cause);
    }
}
