package com.nocommittoday.techswipe.batch.domain.subscription.exception;

import com.nocommittoday.techswipe.batch.domain.core.BatchDomainException;

public class LocalDateParseException extends BatchDomainException {

    public LocalDateParseException(String text) {
        super("날짜 형식 변환 실패. text=" + text);
    }
}
