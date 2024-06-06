package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.param.DateJobParameters;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscribedContentListQuery;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscribedContentResult;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ContentCollectJobItemProcessor implements ItemProcessor<TechContentProviderEntity, List<CollectedContentEntity>> {

    private static final Map<SubscriptionType, CollectionType> SUBSCRIPTION_TYPE_TO_COLLECTION_TYPE = Map.of(
            SubscriptionType.LIST_CRAWLING, CollectionType.LIST_CRAWLING,
            SubscriptionType.RSS, CollectionType.RSS,
            SubscriptionType.ATOM, CollectionType.ATOM
    );

    private final SubscribedContentListQuery subscribedContentListQuery;
    private final DateJobParameters dateJobParameters;

    @Override
    public List<CollectedContentEntity> process(final TechContentProviderEntity item) throws Exception {
        final TechContentProvider.TechContentProviderId providerId = new TechContentProvider.TechContentProviderId(
                item.getId());
        final List<SubscribedContentResult> subscribedContentList = subscribedContentListQuery.getList(
                providerId, dateJobParameters.getDate());
        return subscribedContentList.stream()
                .map(subscribedContent -> new ContentCollect(
                        SUBSCRIPTION_TYPE_TO_COLLECTION_TYPE.get(subscribedContent.type()),
                        providerId,
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
