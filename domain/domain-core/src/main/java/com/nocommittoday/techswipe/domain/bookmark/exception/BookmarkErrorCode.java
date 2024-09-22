package com.nocommittoday.techswipe.domain.bookmark.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum BookmarkErrorCode implements ErrorCodeType {
    ILLEGAL_GROUP_NAME("이미 존재하거나 잘못된 북마크 그룹 이름입니다.", HttpURLConnection.HTTP_CONFLICT),
    GROUP_NOT_FOUND("존재하지 않는 그룹입니다.", HttpURLConnection.HTTP_NOT_FOUND),
    ALREADY_EXISTS("이미 존재하는 북마크입니다.", HttpURLConnection.HTTP_CONFLICT),
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
