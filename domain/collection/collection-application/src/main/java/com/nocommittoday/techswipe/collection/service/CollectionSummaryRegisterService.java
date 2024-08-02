package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionSummaryFormatNotRegistrableException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentReader;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUpdater;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationPromptCreator;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationValidator;
import org.springframework.stereotype.Service;

@Service
public class CollectionSummaryRegisterService {

    private final CollectedContentReader collectedContentReader;
    private final SummarizationPromptCreator summarizationPromptCreator;
    private final SummarizationValidator summarizationValidator;
    private final CollectedContentUpdater collectedContentUpdater;

    public CollectionSummaryRegisterService(
            CollectedContentReader collectedContentReader,
            SummarizationPromptCreator summarizationPromptCreator,
            SummarizationValidator summarizationValidator,
            CollectedContentUpdater collectedContentUpdater
    ) {
        this.collectedContentReader = collectedContentReader;
        this.summarizationPromptCreator = summarizationPromptCreator;
        this.summarizationValidator = summarizationValidator;
        this.collectedContentUpdater = collectedContentUpdater;
    }

    public CollectionSummarizationPromptResult getPrompt(CollectedContentId id) {
        CollectedContent collectedContent = collectedContentReader.get(id);
        String prompt = summarizationPromptCreator.create(collectedContent);
        return new CollectionSummarizationPromptResult(prompt);
    }

    public void register(CollectionSummaryRegisterCommand command) {
        if (!summarizationValidator.check(command.summary())) {
            throw new CollectionSummaryFormatNotRegistrableException(command.id(), command.summary());
        }
        CollectedContent collectedContent = collectedContentReader.get(command.id());
        collectedContentUpdater.update(collectedContent.summarize(command.summary()));
    }
}
