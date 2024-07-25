package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CollectedUrlSetReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public Set<String> getUrlSetByUrls(Collection<String> urls) {
        return Set.copyOf(collectedContentJpaRepository.findAllUrlByUrlIn(urls));
    }
}
