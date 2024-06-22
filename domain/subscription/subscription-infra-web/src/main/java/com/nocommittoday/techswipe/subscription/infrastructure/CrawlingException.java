package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.Crawling;

public class CrawlingException extends RuntimeException {

    public CrawlingException(final String url, final Crawling crawling) {
        super("크롤링에 실패했습니다. url: " + url + ", crawling: " + crawling);
    }

    public CrawlingException(final String url, final Crawling crawling, final String result) {
        super("크롤링에 실패했습니다. url: " + url + ", crawling: " + crawling + ", result: " + result);
    }
}
