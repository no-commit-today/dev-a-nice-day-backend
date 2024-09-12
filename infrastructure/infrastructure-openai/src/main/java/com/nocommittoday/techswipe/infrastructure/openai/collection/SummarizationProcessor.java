package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.content.Summary;
import org.springframework.stereotype.Component;

@Component
public class SummarizationProcessor {

    private final SummarizationClient summarizationClient;

    public SummarizationProcessor(
            SummarizationClient summarizationClient
    ) {
        this.summarizationClient = summarizationClient;
    }

    public Summary summarize(CollectedContent collectedContent) {
        String responseContent = summarizationClient.summarize(collectedContent);
        return Summary.create(responseContent);
    }
}
