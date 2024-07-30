package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectedContentUrlListReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

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
