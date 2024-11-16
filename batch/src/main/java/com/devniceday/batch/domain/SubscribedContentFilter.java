package com.devniceday.batch.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;


public class SubscribedContentFilter {

    private static final Logger log = LoggerFactory.getLogger(SubscribedContentFilter.class);

    private final Set<String> urlSet;

    private final List<SubscribedContent> contents;

    SubscribedContentFilter(Set<String> urlSet, List<SubscribedContent> contents) {
        this.urlSet = urlSet;
        this.contents = contents;
    }

    public boolean hasNext() {
        return urlSet.isEmpty() && !contents.isEmpty();
    }

    public List<SubscribedContent> filter() {
        List<SubscribedContent> filtered = contents.stream()
                .filter(content -> !urlSet.contains(content.url()))
                .toList();
        if (log.isDebugEnabled()) {
            contents.stream()
                    .filter(content -> urlSet.contains(content.url()))
                    .forEach(content ->
                            log.debug("subscriptionId[{}] url[{}] 이 존재합니다.",
                                    content.subscriptionId(), content.url())
                    );
        }
        return filtered;
    }
}
