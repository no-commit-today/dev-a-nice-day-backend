package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.infrastructure.CollectedUrlFilter;
import com.nocommittoday.techswipe.batch.infrastructure.CollectedUrlFilterCreator;
import com.nocommittoday.techswipe.domain.collection.ContentCollect;
import com.nocommittoday.techswipe.domain.collection.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.util.List;

public class ContentCollectJobItemProcessor implements ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> {

    private static final Logger log = LoggerFactory.getLogger(ContentCollectJobItemProcessor.class);

    private final SubscribedContentListQueryService subscribedContentListQueryService;
    private final CollectedUrlFilterCreator collectedUrlFilterCreator;
    private final CollectedContentIdGenerator collectedContentIdGenerator;
    private final CollectedContentEntityMapper collectedContentEntityMapper;
    private final LocalDate date;

    public ContentCollectJobItemProcessor(
            SubscribedContentListQueryService subscribedContentListQueryService,
            CollectedUrlFilterCreator collectedUrlFilterCreator,
            CollectedContentIdGenerator collectedContentIdGenerator,
            CollectedContentEntityMapper collectedContentEntityMapper,
            LocalDate date
    ) {
        this.subscribedContentListQueryService = subscribedContentListQueryService;
        this.collectedUrlFilterCreator = collectedUrlFilterCreator;
        this.collectedContentIdGenerator = collectedContentIdGenerator;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
        this.date = date;
    }

    @Override
    public List<CollectedContentEntity> process(SubscriptionEntity item) throws Exception {
        Subscription subscription = item.toDomain();
        List<SubscribedContent> subscribedContentList = subscribedContentListQueryService
                .getList(subscription, date);
        CollectedUrlFilter urlFilter = collectedUrlFilterCreator.createFromContents(subscribedContentList);
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
