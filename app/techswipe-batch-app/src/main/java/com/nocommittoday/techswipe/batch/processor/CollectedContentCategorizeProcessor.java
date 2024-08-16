package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.infrastructure.collection.CategorizationProcessor;
import com.nocommittoday.techswipe.infrastructure.collection.CategorizationResult;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CollectedContentCategorizeProcessor implements
        ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentCategorizeProcessor.class);

    private final CategorizationProcessor categorizationProcessor;
    private final CollectedContentEntityMapper collectedContentEntityMapper;

    public CollectedContentCategorizeProcessor(
            CategorizationProcessor categorizationProcessor,
            CollectedContentEntityMapper collectedContentEntityMapper
    ) {
        this.categorizationProcessor = categorizationProcessor;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        log.debug("Processing item: {}", item.getId());
        CollectedContent collectedContent = item.toDomain();
        CategorizationResult categorizationResult = categorizationProcessor.categorize(collectedContent);
        if (!categorizationResult.success()) {
            log.error("카테고리 분류 실패 id={}", collectedContent.getId(), categorizationResult.exception());
            CollectedContent categorizationFailed = collectedContent.failCategorization();
            return collectedContentEntityMapper.from(categorizationFailed);
        }
        CollectedContent categorized = collectedContent.categorize(categorizationResult.categoryList());
        return collectedContentEntityMapper.from(categorized);
    }
}
