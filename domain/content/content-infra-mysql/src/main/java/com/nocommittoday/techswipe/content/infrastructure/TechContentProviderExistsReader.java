package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
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
