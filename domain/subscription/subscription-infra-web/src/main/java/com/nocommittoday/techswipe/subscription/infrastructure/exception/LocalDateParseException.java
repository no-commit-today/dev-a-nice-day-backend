package com.nocommittoday.techswipe.subscription.infrastructure.exception;

public class LocalDateParseException extends CollectionInfrastructureWebException {

    public LocalDateParseException(String text) {
        super("날짜 형식 변환 실패. text=" + text);
    }
}
