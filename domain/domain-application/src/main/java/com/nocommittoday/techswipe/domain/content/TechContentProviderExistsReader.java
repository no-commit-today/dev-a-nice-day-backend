package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentProviderExistsReader {

    private final TechContentProviderJpaRepository repository;

    public TechContentProviderExistsReader(TechContentProviderJpaRepository repository) {
        this.repository = repository;
    }

    public boolean exists(TechContentProviderId id) {
        return repository.existsByIdAndDeletedIsFalse(id.value());
    }
}
