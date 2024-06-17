package com.nocommittoday.techswipe.subscription.infrastructure;

public class LocalDateParseException extends RuntimeException {

    public LocalDateParseException(final String text) {
        super("날짜 형식 변환 실패. text=" + text);
    }
}
