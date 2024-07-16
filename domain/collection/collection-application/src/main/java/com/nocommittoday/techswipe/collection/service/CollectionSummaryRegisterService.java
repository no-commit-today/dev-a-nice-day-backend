package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentReader;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationPromptCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionSummaryRegisterService {

    private final CollectedContentReader collectedContentReader;

    private final SummarizationPromptCreator summarizationPromptCreator;

    public CollectionSummarizationPromptResult getPrompt(final CollectedContent.Id id) {
        final CollectedContent collectedContent = collectedContentReader.get(id);
        final String prompt = summarizationPromptCreator.create(collectedContent);
        return new CollectionSummarizationPromptResult(prompt);
    }
}
