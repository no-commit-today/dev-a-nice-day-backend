package com.nocommittoday.techswipe.storage.mysql.batch;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BatchCollectedContentEntityMapper {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public BatchCollectedContentEntityMapper(
            TechContentProviderJpaRepository techContentProviderJpaRepository,
            SubscriptionJpaRepository subscriptionJpaRepository
    ) {
        this.techContentProviderJpaRepository = techContentProviderJpaRepository;
        this.subscriptionJpaRepository = subscriptionJpaRepository;
    }

    public CollectedContentEntity from(CollectedContent collectedContent) {
        return new CollectedContentEntity(
                collectedContent.getId().value(),
                collectedContent.getStatus(),
                techContentProviderJpaRepository.getReferenceById(collectedContent.getProviderId().value()),
                subscriptionJpaRepository.getReferenceById(collectedContent.getSubscriptionId().value()),
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                collectedContent.getContent(),
                collectedContent.getImageUrl(),
                collectedContent.getCategoryList() != null ? collectedContent.getCategoryList().getContent() : null,
                collectedContent.getSummary() != null ? collectedContent.getSummary().getContent() : null
        );
    }
}
