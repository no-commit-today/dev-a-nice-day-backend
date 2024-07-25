package com.nocommittoday.techswipe.batch.infrastructure;

import com.nocommittoday.techswipe.collection.infrastructure.CollectedUrlSetReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CollectedUrlFilterCreator {

    private final CollectedUrlSetReader collectedUrlSetReader;

    public CollectedUrlFilter create(final Collection<String> urls) {
        return new CollectedUrlFilter(collectedUrlSetReader.getUrlSetByUrls(urls));
    }
}
