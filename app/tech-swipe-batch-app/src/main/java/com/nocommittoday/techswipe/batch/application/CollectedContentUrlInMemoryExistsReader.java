package com.nocommittoday.techswipe.batch.application;

import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUrlListReader;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Slf4j
public class CollectedContentUrlInMemoryExistsReader {

    private final Set<String> urlSet;

    public CollectedContentUrlInMemoryExistsReader(
            final CollectedContentUrlListReader urlListReader,
            @Nullable final List<TechContentProvider.Id> providerIds
    ) {
        if (providerIds == null) {
            this.urlSet = Set.copyOf(urlListReader.getAllUrls());
            return;
        }
        this.urlSet = Set.copyOf(urlListReader.getAllUrlsByProviders(providerIds));
    }

    public boolean exists(final String url) {
        return urlSet.contains(url);
    }
}
