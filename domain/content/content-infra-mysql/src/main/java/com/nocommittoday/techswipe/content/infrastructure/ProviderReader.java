package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProviderReader {

    private final TechContentProviderJpaRepository providerJpaRepository;

    public TechContentProvider get(final TechContentProvider.TechContentProviderId id) {
        return providerJpaRepository.findById(id.value())
                .filter(TechContentProviderEntity::isUsed)
                .map(TechContentProviderEntity::toDomain)
                .orElseThrow(() -> new TechContentProviderNotFoundException(id));
    }
}
