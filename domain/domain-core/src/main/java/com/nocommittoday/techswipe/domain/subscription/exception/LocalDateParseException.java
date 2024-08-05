package com.nocommittoday.techswipe.domain.subscription.exception;

public class LocalDateParseException extends CollectionInfrastructureWebException {

    public LocalDateParseException(String text) {
        super("날짜 형식 변환 실패. text=" + text);
    }
}
