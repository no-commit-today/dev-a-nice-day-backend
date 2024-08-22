package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.domain.collection.BatchCollectedContentIdGenerator;
import com.nocommittoday.techswipe.batch.domain.collection.BatchSubscribedContentCollectService;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchCollectedContentEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class ContentCollectJobItemProcessor implements ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> {

    private final BatchSubscribedContentCollectService subscribedContentCollectService;
    private final BatchCollectedContentIdGenerator collectedContentIdGenerator;
    private final BatchCollectedContentEntityMapper collectedContentEntityMapper;

    public ContentCollectJobItemProcessor(
            BatchSubscribedContentCollectService subscribedContentCollectService,
            BatchCollectedContentIdGenerator collectedContentIdGenerator,
            BatchCollectedContentEntityMapper collectedContentEntityMapper
    ) {
        this.subscribedContentCollectService = subscribedContentCollectService;
        this.collectedContentIdGenerator = collectedContentIdGenerator;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
    }

    @Override
    public List<CollectedContentEntity> process(SubscriptionEntity item) throws Exception {
        Subscription subscription = item.toDomain();
        List<SubscribedContent> subscribedContents = subscribedContentCollectService.getList(subscription);
        return subscribedContents.stream()
                .map(content -> CollectedContent.collect(
                        collectedContentIdGenerator.nextId(),
                        content.isInitialized(),
                        subscription.getProviderId(),
                        subscription.getId(),
                        content.getUrl(),
                        content.getTitle(),
                        content.getPublishedDate(),
                        content.getContent(),
                        content.getImageUrl()
                ))
                .map(collectedContentEntityMapper::from)
                .toList();
    }
}
