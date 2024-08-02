package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentProviderReader {

    private final TechContentProviderJpaRepository providerJpaRepository;

    public TechContentProviderReader(TechContentProviderJpaRepository providerJpaRepository) {
        this.providerJpaRepository = providerJpaRepository;
    }

    public TechContentProvider get(TechContentProviderId id) {
        return providerJpaRepository.findById(id.value())
                .filter(TechContentProviderEntity::isUsed)
                .map(TechContentProviderEntity::toDomain)
                .orElseThrow(() -> new TechContentProviderNotFoundException(id));
    }
}
