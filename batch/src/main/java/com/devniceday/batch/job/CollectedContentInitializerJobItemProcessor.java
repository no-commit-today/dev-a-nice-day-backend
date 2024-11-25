package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectionStatus;
import com.devniceday.batch.domain.ContentInitialization;
import com.devniceday.batch.domain.ContentInitializer;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.storage.db.core.BatchCollectedContentAndSubscriptionProjection;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

public class CollectedContentInitializerJobItemProcessor
        implements ItemProcessor<BatchCollectedContentAndSubscriptionProjection, BatchCollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentInitializerJobItemProcessor.class);

    private static final int MIN_TOKEN_COUNT = 100;

    private final SubscriptionMapper subscriptionMapper;
    private final ContentInitializer contentInitializer;

    public CollectedContentInitializerJobItemProcessor(
            SubscriptionMapper subscriptionMapper,
            ContentInitializer contentInitializer
    ) {
        this.subscriptionMapper = subscriptionMapper;
        this.contentInitializer = contentInitializer;
    }

    @Override
    public BatchCollectedContentEntity process(BatchCollectedContentAndSubscriptionProjection item)
            throws Exception {
        Subscription subscription = subscriptionMapper.map(item.getSubscription());
        ContentInitialization contentInitialization = contentInitializer
                .initialize(subscription, item.getCollectedContent().getUrl());
        BatchCollectedContentEntity entity = item.getCollectedContent();

        initialize(contentInitialization, entity);
        filter(entity);
        return entity;
    }

    private static void initialize(ContentInitialization contentInitialization, BatchCollectedContentEntity entity) {
        if (contentInitialization.title() != null) {
            entity.setTitle(contentInitialization.title());
        }
        if (contentInitialization.publishedDate() != null) {
            entity.setPublishedDate(contentInitialization.publishedDate());
        }
        if (contentInitialization.imageUrl() != null) {
            entity.setImageUrl(contentInitialization.imageUrl());
        }
        if (contentInitialization.content() != null) {
            entity.setContent(contentInitialization.content());
        }
        entity.setStatus(CollectionStatus.INIT);
    }

    private void filter(BatchCollectedContentEntity entity) {
        if (!StringUtils.hasText(entity.getTitle())) {
            log.info("제목이 없어서 필터링 되었습니다. id={}", entity.getId());
            entity.setStatus(CollectionStatus.FILTERED);
        }
        if (entity.getPublishedDate() == null) {
            log.info("발행일이 없어서 필터링 되었습니다. id={}", entity.getId());
            entity.setStatus(CollectionStatus.FILTERED);
        }
        if (!StringUtils.hasText(entity.getContent())) {
            log.info("컨텐츠가 없어서 필터링 되었습니다. id={}", entity.getId());
            entity.setStatus(CollectionStatus.FILTERED);
        }
        if (calculateTokenCount(entity.getContent()) < MIN_TOKEN_COUNT) {
            log.info("컨텐츠 토큰 수가 " + MIN_TOKEN_COUNT + "개 미만이어서 필터링 되었습니다. id={}", entity.getId());
            entity.setStatus(CollectionStatus.FILTERED);
        }
    }

    public int calculateTokenCount(String content) {
        return (int) Arrays.stream(Objects.requireNonNull(content).split("[ \t\n]"))
                .filter(token -> !token.isBlank())
                .count();
    }
}
