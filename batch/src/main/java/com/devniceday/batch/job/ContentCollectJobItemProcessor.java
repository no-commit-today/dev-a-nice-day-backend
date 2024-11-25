package com.devniceday.batch.job;

import com.devniceday.batch.domain.ContentSubscriber;
import com.devniceday.batch.domain.SubscribedContent;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.module.idgenerator.IdGenerator;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class ContentCollectJobItemProcessor
        implements ItemProcessor<BatchSubscriptionEntity, List<BatchCollectedContentEntity>> {

    private final IdGenerator idGenerator;
    private final SubscriptionMapper subscriptionMapper;
    private final ContentSubscriber contentSubscriber;

    public ContentCollectJobItemProcessor(
            IdGenerator idGenerator,
            SubscriptionMapper subscriptionMapper,
            ContentSubscriber contentSubscriber
    ) {
        this.idGenerator = idGenerator;
        this.subscriptionMapper = subscriptionMapper;
        this.contentSubscriber = contentSubscriber;
    }

    @Override
    public List<BatchCollectedContentEntity> process(BatchSubscriptionEntity item) throws Exception {
        Subscription subscription = subscriptionMapper.map(item);
        List<SubscribedContent> subscribedContents = contentSubscriber.subscribe(subscription);
        return subscribedContents.stream()
                .map(content -> new BatchCollectedContentEntity(
                                idGenerator.nextId(),
                                subscription.providerId(),
                                subscription.id(),
                                content.url(),
                                content.title(),
                                content.publishedDate(),
                                content.content(),
                                content.imageUrl()
                        )
                )
                .toList();
    }

}
