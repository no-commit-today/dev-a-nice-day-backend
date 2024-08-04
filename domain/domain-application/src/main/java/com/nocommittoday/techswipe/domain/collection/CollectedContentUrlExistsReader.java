package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CollectedContentUrlExistsReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public CollectedContentUrlExistsReader(CollectedContentJpaRepository collectedContentJpaRepository) {
        this.collectedContentJpaRepository = collectedContentJpaRepository;
    }

    public boolean exists(String url) {
        return collectedContentJpaRepository.existsByUrl(url);
    }
}
