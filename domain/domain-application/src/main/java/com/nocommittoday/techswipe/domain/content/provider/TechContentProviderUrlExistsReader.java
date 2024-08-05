package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
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
