package com.nocommittoday.techswipe.subscription.infrastructure.exception;

import com.nocommittoday.techswipe.subscription.domain.Crawling;

public class CrawlingException extends CollectionInfrastructureWebException {

    public CrawlingException(String url, Crawling crawling) {
        super("크롤링에 실패했습니다. url: " + url + ", crawling: " + crawling);
    }

    public CrawlingException(String url, Crawling crawling, Exception cause) {
        super("크롤링에 실패했습니다. url: " + url + ", crawling: " + crawling, cause);
    }
}
