package com.devniceday.batch.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContentSubscriber {

    private static final Logger log = LoggerFactory.getLogger(ContentSubscriber.class);

    private static final int MAX_PAGE = 10;

    private final SubscribedContentFetcher contentFetcher;
    private final SubscribedContentFilterCreator filterCreator;

    public ContentSubscriber(SubscribedContentFetcher contentFetcher, SubscribedContentFilterCreator filterCreator) {
        this.contentFetcher = contentFetcher;
        this.filterCreator = filterCreator;
    }

    public List<SubscribedContent> subscribe(Subscription subscription) {
        List<SubscribedContent> contents = new ArrayList<>();
        for (int page = 1; page <= MAX_PAGE; page++) {
            List<SubscribedContent> currentPageContents = contentFetcher.fetch(subscription, page);
            SubscribedContentFilter contentFilter = filterCreator.create(currentPageContents);
            contents.addAll(contentFilter.filter());

            if (!contentFilter.hasNext()) {
                break;
            }

            if (page == MAX_PAGE && contentFilter.hasNext()) {
                log.warn("컨텐츠 수집이 최대 페이지[page={}]에 도달했으나, 수집이 완료되지 않았습니다.", page);
            }
        }
        return contents;
    }
}
