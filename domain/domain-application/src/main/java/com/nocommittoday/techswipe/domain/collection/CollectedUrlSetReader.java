package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public class CollectedUrlSetReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public CollectedUrlSetReader(CollectedContentJpaRepository collectedContentJpaRepository) {
        this.collectedContentJpaRepository = collectedContentJpaRepository;
    }

    public Set<String> getUrlSetByUrls(Collection<String> urls) {
        return Set.copyOf(collectedContentJpaRepository.findAllUrlByUrlIn(urls));
    }
}
