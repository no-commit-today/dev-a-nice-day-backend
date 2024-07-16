package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionSummaryFormatNotRegistrableException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentReader;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUpdater;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationPromptCreator;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionSummaryRegisterService {

    private final CollectedContentReader collectedContentReader;

    private final SummarizationPromptCreator summarizationPromptCreator;

    private final SummarizationValidator summarizationValidator;

    private final CollectedContentUpdater collectedContentUpdater;

    public CollectionSummarizationPromptResult getPrompt(final CollectedContent.Id id) {
        final CollectedContent collectedContent = collectedContentReader.get(id);
        final String prompt = summarizationPromptCreator.create(collectedContent);
        return new CollectionSummarizationPromptResult(prompt);
    }

    public void register(final CollectionSummaryRegisterCommand command) {
        if (!summarizationValidator.check(command.summary())) {
            throw new CollectionSummaryFormatNotRegistrableException(command.id(), command.summary());
        }
        final CollectedContent collectedContent = collectedContentReader.get(command.id());
        collectedContentUpdater.update(collectedContent.summarize(command.summary()));
    }
}
