package com.nocommittoday.techswipe.batch.infrastructure;

import com.nocommittoday.techswipe.collection.infrastructure.CollectedUrlSetReader;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CollectedUrlFilterCreator {

    private final CollectedUrlSetReader collectedUrlSetReader;

    public CollectedUrlFilter createFromContents(final Collection<SubscribedContent> contents) {
        final List<String> urls = contents.stream()
                .map(SubscribedContent::url)
                .toList();
        return createFromUrls(urls);
    }

    public CollectedUrlFilter createFromUrls(final Collection<String> urls) {
        return new CollectedUrlFilter(collectedUrlSetReader.getUrlSetByUrls(urls));
    }
}
