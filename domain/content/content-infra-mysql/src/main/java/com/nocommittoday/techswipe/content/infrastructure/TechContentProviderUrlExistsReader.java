package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechContentProviderUrlExistsReader {

    private final TechContentProviderJpaRepository repository;

    public boolean exists(final String url) {
        return repository.existsByUrl(url);
    }
}
