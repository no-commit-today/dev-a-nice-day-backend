package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CollectedContentUrlListReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public CollectedContentUrlListReader(CollectedContentJpaRepository collectedContentJpaRepository) {
        this.collectedContentJpaRepository = collectedContentJpaRepository;
    }

    public List<String> getAllUrls() {
        return collectedContentJpaRepository.findAllUrl();
    }

    public List<String> getAllUrlsByProvider(TechContentProviderId providerId) {
        return collectedContentJpaRepository.findAllUrlByProvider(TechContentProviderEntity.from(providerId));
    }

    public List<String> getAllUrlsByProviders(List<TechContentProviderId> providerIds) {
        return collectedContentJpaRepository.findAllUrlByProviderIn(
                providerIds.stream()
                        .map(TechContentProviderEntity::from)
                        .toList()
        );
    }
}
