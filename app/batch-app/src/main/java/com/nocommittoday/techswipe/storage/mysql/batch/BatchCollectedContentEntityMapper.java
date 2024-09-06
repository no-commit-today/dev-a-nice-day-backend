package com.nocommittoday.techswipe.storage.mysql.batch;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BatchCollectedContentEntityMapper {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    private final TechContentJpaRepository techContentJpaRepository;

    public BatchCollectedContentEntityMapper(
            TechContentProviderJpaRepository techContentProviderJpaRepository,
            SubscriptionJpaRepository subscriptionJpaRepository,
            TechContentJpaRepository techContentJpaRepository
    ) {
        this.techContentProviderJpaRepository = techContentProviderJpaRepository;
        this.subscriptionJpaRepository = subscriptionJpaRepository;
        this.techContentJpaRepository = techContentJpaRepository;
    }

    public CollectedContentEntity from(CollectedContent collectedContent) {
        return new CollectedContentEntity(
                collectedContent.getId().value(),
                collectedContent.getStatus(),
                techContentProviderJpaRepository.getReferenceById(collectedContent.getProviderId().value()),
                subscriptionJpaRepository.getReferenceById(collectedContent.getSubscriptionId().value()),
                collectedContent.getPublishedContentId() != null
                        ? techContentJpaRepository.getReferenceById(collectedContent.getPublishedContentId().value())
                        : null,
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
