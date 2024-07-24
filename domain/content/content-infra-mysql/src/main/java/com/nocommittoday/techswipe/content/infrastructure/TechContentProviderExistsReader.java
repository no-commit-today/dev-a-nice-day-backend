package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechContentProviderExistsReader {

    private final TechContentProviderJpaRepository repository;

    public boolean exists(final TechContentProviderId id) {
        return repository.existsByIdAndDeletedIsFalse(id.value());
    }
}
