package com.nocommittoday.techswipe.domain.core;

import javax.annotation.Nullable;

public class DomainValidationException extends RuntimeException {

    @Nullable
    private final Object data;

    public DomainValidationException(String message) {
        super(message);
        this.data = null;
    }

    public DomainValidationException(String message, @Nullable Object data) {
        super(message + " data=" + data);
        this.data = data;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
