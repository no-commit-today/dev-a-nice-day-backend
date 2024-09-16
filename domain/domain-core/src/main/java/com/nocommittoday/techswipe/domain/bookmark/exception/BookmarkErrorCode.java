package com.nocommittoday.techswipe.domain.bookmark.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

public enum BookmarkErrorCode implements ErrorCodeType {
    ;

    private final String message;
    private final int status;

    BookmarkErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return "BOOKMARK-" + name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
