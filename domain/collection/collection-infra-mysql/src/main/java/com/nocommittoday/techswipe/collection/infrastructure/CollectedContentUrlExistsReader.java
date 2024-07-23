package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CollectedContentUrlExistsReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public boolean exists(final String url) {
        return collectedContentJpaRepository.existsByUrl(url);
    }
}
