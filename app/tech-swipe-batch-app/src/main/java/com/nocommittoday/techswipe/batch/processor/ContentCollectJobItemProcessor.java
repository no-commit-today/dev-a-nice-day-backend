package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContentResult;
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

    private final CollectedContentIdGenerator collectedContentIdGenerator;

    private final LocalDate date;

    @Override
    public List<CollectedContentEntity> process(final SubscriptionEntity item) throws Exception {
        final Subscription subscription = item.toDomain();
        final List<SubscribedContentResult> subscribedContentList = subscribedContentListQueryService.getList(
                subscription, date);
        return subscribedContentList.stream()
                .filter(subscribedContent -> {
                    if (!subscribedContent.success()) {
                        log.warn("구독 컨텐츠 수집 실패 subscription.id={}, url={}",
                                subscription.getId(), subscribedContent.content().url(), subscribedContent.exception()
                        );
                    }
                    return subscribedContent.success();
                })
                .map(subscribedContent -> new ContentCollect(
                        collectedContentIdGenerator.nextId(),
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
