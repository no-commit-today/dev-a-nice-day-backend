package com.nocommittoday.techswipe.subscription.infrastructure.exception;

import com.nocommittoday.techswipe.subscription.domain.CrawlingType;

public class UnsupportedCrawlingTypeException extends CollectionInfrastructureWebException {

    public UnsupportedCrawlingTypeException(CrawlingType type) {
        super("지원하지 않는 크롤링 타입입니다. type=" + type);
    }
}
