package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionSummaryFormatNotRegistrableException;
import com.nocommittoday.techswipe.domain.content.Summary;
import org.springframework.stereotype.Service;

@Service
public class CollectionSummaryRegisterService {

    private final CollectedContentReader collectedContentReader;
    private final CollectedContentUpdater collectedContentUpdater;

    public CollectionSummaryRegisterService(
            CollectedContentReader collectedContentReader,
            CollectedContentUpdater collectedContentUpdater
    ) {
        this.collectedContentReader = collectedContentReader;
        this.collectedContentUpdater = collectedContentUpdater;
    }

    public CollectionSummarizationPromptResult getPrompt(CollectedContentId id) {
        CollectedContent collectedContent = collectedContentReader.get(id);
        SummarizationPrompt prompt = SummarizationPrompt.of(collectedContent);
        return new CollectionSummarizationPromptResult(prompt.getContent());
    }

    public void register(CollectionSummaryRegisterCommand command) {
        Summary summary = new Summary(command.summary());
        if (!summary.isValid()) {
            throw new CollectionSummaryFormatNotRegistrableException(command.id(), command.summary());
        }
        CollectedContent collectedContent = collectedContentReader.get(command.id());
        collectedContentUpdater.update(collectedContent.summarize(summary.getContent()));
    }
}
