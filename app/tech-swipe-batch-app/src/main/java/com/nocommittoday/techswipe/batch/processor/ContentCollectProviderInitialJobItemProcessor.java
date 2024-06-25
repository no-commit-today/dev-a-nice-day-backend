package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.application.CollectedContentUrlInMemoryExistsReader;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContentResult;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ContentCollectProviderInitialJobItemProcessor implements ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> {

    private final SubscribedContentListQueryService subscribedContentListQueryService;
    private final CollectedContentUrlInMemoryExistsReader urlExistsReader;

    @Override
    public List<CollectedContentEntity> process(final SubscriptionEntity item) throws Exception {
        final Subscription subscription = item.toDomain();
        final List<SubscribedContentResult> subscribedContentList = subscribedContentListQueryService
                .getInitList(subscription);
        return subscribedContentList.stream()
                .filter(subscribedContent -> {
                    final boolean exists = urlExistsReader.exists(subscribedContent.content().url());
                    if (exists) {
                        log.info("이미 수집된 URL: {}", subscribedContent.content().url());
                    }
                    return !exists;
                })
                .map(subscribedContent -> new ContentCollect(
                        subscription.getProviderId(),
                        subscribedContent.content().url(),
                        subscribedContent.content().title(),
                        subscribedContent.content().publishedDate(),
                        subscribedContent.content().content(),
                        subscribedContent.content().imageUrl()
                ))
                .map(CollectedContentEntity::from)
                .toList();
    }
}
