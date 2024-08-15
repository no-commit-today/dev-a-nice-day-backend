package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.infrastructure.collection.SummarizationProcessor;
import com.nocommittoday.techswipe.infrastructure.collection.SummarizationResult;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CollectedContentSummarizeProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentSummarizeProcessor.class);

    private final SummarizationProcessor summarizationProcessor;
    private final CollectedContentEntityMapper collectedContentEntityMapper;

    public CollectedContentSummarizeProcessor(
            SummarizationProcessor summarizationProcessor,
            CollectedContentEntityMapper collectedContentEntityMapper
    ) {
        this.summarizationProcessor = summarizationProcessor;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContent collectedContent = item.toDomain();
        SummarizationResult summarizationResult = summarizationProcessor.summarize(collectedContent);

        if (!summarizationResult.success()) {
            log.error("요약 실패 id={}", collectedContent.getId(), summarizationResult.exception());
            CollectedContent summarizationFailed = collectedContent.failSummarization();
            return collectedContentEntityMapper.from(summarizationFailed);
        }

        CollectedContent summarized = collectedContent.summarize(summarizationResult.summary().getContent());
        return collectedContentEntityMapper.from(summarized);
    }
}
