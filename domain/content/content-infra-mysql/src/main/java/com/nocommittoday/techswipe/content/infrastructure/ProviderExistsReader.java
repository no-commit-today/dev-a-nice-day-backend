package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProviderExistsReader {

    private final TechContentJpaRepository repository;

    public boolean exists(final TechContentProvider.TechContentProviderId id) {
        return repository.existsByIdAndDeletedIsFalse(id.value());
    }
}
