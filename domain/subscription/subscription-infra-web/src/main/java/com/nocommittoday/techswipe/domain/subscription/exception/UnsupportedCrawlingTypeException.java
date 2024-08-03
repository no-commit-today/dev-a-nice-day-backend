package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.domain.subscription.CrawlingType;

public class UnsupportedCrawlingTypeException extends CollectionInfrastructureWebException {

    public UnsupportedCrawlingTypeException(CrawlingType type) {
        super("지원하지 않는 크롤링 타입입니다. type=" + type);
    }
}
