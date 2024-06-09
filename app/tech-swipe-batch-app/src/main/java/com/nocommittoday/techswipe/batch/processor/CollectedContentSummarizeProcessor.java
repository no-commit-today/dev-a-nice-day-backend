package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.application.PromptWithInMemoryCacheReader;
import com.nocommittoday.techswipe.batch.exception.SummarizeFailureException;
import com.nocommittoday.techswipe.collection.application.port.out.SummarizationResult;
import com.nocommittoday.techswipe.collection.application.port.out.SummarizePort;
import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class CollectedContentSummarizeProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final PromptWithInMemoryCacheReader promptReader;
    private final SummarizePort summarizePort;

    @Override
    public CollectedContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        final Prompt prompt = promptReader.get(PromptType.SUMMARIZE, item.getProvider().getType());
        final SummarizationResult summarizationResult = summarizePort.summarize(prompt, collectedContent.getContent());
        if (!summarizationResult.success()) {
            throw new SummarizeFailureException(collectedContent.getId(), prompt.getId());
        }

        final CollectedContent summarized = collectedContent.summarize(summarizationResult.summary());
        return CollectedContentEntity.from(summarized);
    }
}
