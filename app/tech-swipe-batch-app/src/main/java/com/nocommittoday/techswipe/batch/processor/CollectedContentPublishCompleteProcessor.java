package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import org.springframework.batch.item.ItemProcessor;

public class CollectedContentPublishCompleteProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity>  {

    @Override
    public CollectedContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        collectedContent.published();
        return CollectedContentEntity.from(collectedContent);
    }
}
