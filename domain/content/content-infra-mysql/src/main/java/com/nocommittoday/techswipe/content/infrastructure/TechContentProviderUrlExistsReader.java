package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentProviderUrlExistsReader {

    private final TechContentProviderJpaRepository repository;

    public TechContentProviderUrlExistsReader(TechContentProviderJpaRepository repository) {
        this.repository = repository;
    }

    public boolean exists(String url) {
        return repository.existsByUrl(url);
    }
}
