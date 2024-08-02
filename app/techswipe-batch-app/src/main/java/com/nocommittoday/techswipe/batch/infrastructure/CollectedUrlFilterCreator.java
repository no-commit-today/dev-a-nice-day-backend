package com.nocommittoday.techswipe.batch.infrastructure;

import com.nocommittoday.techswipe.domain.collection.CollectedUrlSetReader;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CollectedUrlFilterCreator {

    private final CollectedUrlSetReader collectedUrlSetReader;

    public CollectedUrlFilterCreator(CollectedUrlSetReader collectedUrlSetReader) {
        this.collectedUrlSetReader = collectedUrlSetReader;
    }

    public CollectedUrlFilter createFromContents(Collection<SubscribedContent> contents) {
        List<String> urls = contents.stream()
                .map(SubscribedContent::url)
                .toList();
        return createFromUrls(urls);
    }

    public CollectedUrlFilter createFromUrls(Collection<String> urls) {
        return new CollectedUrlFilter(collectedUrlSetReader.getUrlSetByUrls(urls));
    }
}
