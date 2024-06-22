package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProviderExistsReader {

    private final TechContentProviderJpaRepository repository;

    public boolean exists(final TechContentProvider.Id id) {
        return repository.existsByIdAndDeletedIsFalse(id.value());
    }
}
