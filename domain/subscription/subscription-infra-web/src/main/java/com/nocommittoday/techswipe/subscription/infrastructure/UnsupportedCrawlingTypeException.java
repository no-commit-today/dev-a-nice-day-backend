package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.CrawlingType;

public class UnsupportedCrawlingTypeException extends RuntimeException {

    public UnsupportedCrawlingTypeException(final CrawlingType type) {
        super("지원하지 않는 크롤링 타입입니다. type=" + type);
    }
}
