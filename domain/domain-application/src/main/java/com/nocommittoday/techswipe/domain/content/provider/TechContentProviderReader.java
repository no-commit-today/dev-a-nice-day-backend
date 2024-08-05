package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.domain.content.TechContentProvider;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
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
