package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationProcessor;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationResult;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class CollectedContentSummarizeProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final SummarizationProcessor summarizationProcessor;

    private final CollectedContentEntityMapper collectedContentEntityMapper;

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContent collectedContent = item.toDomain();
        SummarizationResult summarizationResult = summarizationProcessor.summarize(collectedContent);

        if (!summarizationResult.success()) {
            log.error("요약 실패 id={}", collectedContent.getId(), summarizationResult.exception());
            CollectedContent summarizationFailed = collectedContent.failSummarization();
            return collectedContentEntityMapper.from(summarizationFailed);
        }

        CollectedContent summarized = collectedContent.summarize(summarizationResult.summary());
        return collectedContentEntityMapper.from(summarized);
    }
}
