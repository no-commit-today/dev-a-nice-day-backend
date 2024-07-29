package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.infrastructure.CategorizationProcessor;
import com.nocommittoday.techswipe.collection.infrastructure.CategorizationResult;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class CollectedContentCategorizeProcessor implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final CategorizationProcessor categorizationProcessor;

    private final CollectedContentEntityMapper collectedContentEntityMapper;

    @Override
    public CollectedContentEntity process(final CollectedContentEntity item) throws Exception {
        log.debug("Processing item: {}", item.getId());
        final CollectedContent collectedContent = item.toDomain();
        final CategorizationResult categorizationResult = categorizationProcessor.categorize(collectedContent);
        if (!categorizationResult.success()) {
            log.error("카테고리 분류 실패 id={}", collectedContent.getId(), categorizationResult.exception());
            final CollectedContent categorizationFailed = collectedContent.failCategorization();
            return collectedContentEntityMapper.from(categorizationFailed);
        }
        final CollectedContent categorized = collectedContent.categorize(categorizationResult.categories());
        return collectedContentEntityMapper.from(categorized);
    }
}
