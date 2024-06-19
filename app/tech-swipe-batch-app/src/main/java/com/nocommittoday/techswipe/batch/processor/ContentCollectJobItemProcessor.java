package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectionType;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentResult;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ContentCollectJobItemProcessor implements ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> {

    private static final Map<SubscriptionType, CollectionType> SUBSCRIPTION_TYPE_TO_COLLECTION_TYPE = Map.of(
            SubscriptionType.LIST_CRAWLING, CollectionType.LIST_CRAWLING,
            SubscriptionType.FEED, CollectionType.FEED
    );

    private final SubscribedContentListQueryService subscribedContentListQueryService;
    private final LocalDate date;

    @Override
    public List<CollectedContentEntity> process(final SubscriptionEntity item) throws Exception {
        final Subscription subscription = item.toDomain();
        final List<SubscribedContentResult> subscribedContentList = subscribedContentListQueryService.getList(
                subscription, date);
        return subscribedContentList.stream()
                .map(subscribedContent -> new ContentCollect(
                        SUBSCRIPTION_TYPE_TO_COLLECTION_TYPE.get(subscribedContent.type()),
                        subscription.getProviderId(),
                        subscribedContent.url(),
                        subscribedContent.title(),
                        subscribedContent.publishedDate(),
                        subscribedContent.content(),
                        subscribedContent.imageUrl()
                ))
                .map(CollectedContentEntity::from)
                .toList();
    }
}
