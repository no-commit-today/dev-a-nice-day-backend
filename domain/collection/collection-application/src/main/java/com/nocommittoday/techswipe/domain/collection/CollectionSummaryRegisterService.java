package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionSummaryFormatNotRegistrableException;
import org.springframework.stereotype.Service;

@Service
public class CollectionSummaryRegisterService {

    private final CollectedContentReader collectedContentReader;
    private final SummarizationValidator summarizationValidator;
    private final CollectedContentUpdater collectedContentUpdater;

    public CollectionSummaryRegisterService(
            CollectedContentReader collectedContentReader,
            SummarizationValidator summarizationValidator,
            CollectedContentUpdater collectedContentUpdater
    ) {
        this.collectedContentReader = collectedContentReader;
        this.summarizationValidator = summarizationValidator;
        this.collectedContentUpdater = collectedContentUpdater;
    }

    public CollectionSummarizationPromptResult getPrompt(CollectedContentId id) {
        CollectedContent collectedContent = collectedContentReader.get(id);
        SummarizationPrompt prompt = SummarizationPrompt.of(collectedContent);
        return new CollectionSummarizationPromptResult(prompt.getContent());
    }

    public void register(CollectionSummaryRegisterCommand command) {
        if (!summarizationValidator.check(command.summary())) {
            throw new CollectionSummaryFormatNotRegistrableException(command.id(), command.summary());
        }
        CollectedContent collectedContent = collectedContentReader.get(command.id());
        collectedContentUpdater.update(collectedContent.summarize(command.summary()));
    }
}
