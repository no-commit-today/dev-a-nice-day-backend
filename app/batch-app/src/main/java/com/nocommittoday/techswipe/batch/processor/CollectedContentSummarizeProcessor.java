package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationProcessor;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationResult;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class CollectedContentSummarizeProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final SummarizationProcessor summarizationProcessor;

    @Override
    public CollectedContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        final SummarizationResult summarizationResult = summarizationProcessor.summarize(collectedContent);

        if (!summarizationResult.success()) {
            log.error("요약 실패 id={}", collectedContent.getId(), summarizationResult.exception());
            final CollectedContent summarizationFailed = collectedContent.failSummarization();
            return CollectedContentEntity.from(summarizationFailed);
        }

        final CollectedContent summarized = collectedContent.summarize(summarizationResult.summary());
        return CollectedContentEntity.from(summarized);
    }
}
