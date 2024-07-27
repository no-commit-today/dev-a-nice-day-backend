package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.infrastructure.CollectedUrlFilter;
import com.nocommittoday.techswipe.batch.infrastructure.CollectedUrlFilterCreator;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ContentCollectJobItemProcessor implements ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> {

    private final SubscribedContentListQueryService subscribedContentListQueryService;

    private final CollectedUrlFilterCreator collectedUrlFilterCreator;

    private final CollectedContentIdGenerator collectedContentIdGenerator;

    private final CollectedContentEntityMapper collectedContentEntityMapper;

    private final LocalDate date;

    @Override
    public List<CollectedContentEntity> process(final SubscriptionEntity item) throws Exception {
        final Subscription subscription = item.toDomain();
        final List<SubscribedContent> subscribedContentList = subscribedContentListQueryService
                .getList(subscription, date);
        final CollectedUrlFilter urlFilter = collectedUrlFilterCreator.createFromContents(subscribedContentList);
        return subscribedContentList.stream()
                .filter(subscribedContent -> urlFilter.doFilter(subscribedContent.url()))
                .map(subscribedContent -> new ContentCollect(
                        collectedContentIdGenerator.nextId(),
                        subscription.getProviderId(),
                        subscribedContent.url(),
                        subscribedContent.title(),
                        subscribedContent.publishedDate(),
                        subscribedContent.content(),
                        subscribedContent.imageUrl()
                ))
                .map(collectedContentEntityMapper::from)
                .toList();
    }
}
