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

    public SummarizationResult summarize(CollectedContent collectedContent) {
        try {
            String responseContent = summarizationClient.summarize(collectedContent);
            Summary summary = new Summary(responseContent);
            if (!summary.isValid()) {
                throw new SummarizationResponseInvalidException(
                        "요약 결과가 올바르지 않습니다. ", collectedContent.getId(), responseContent
                );
            }

            return SummarizationResult.success(summary);
        } catch (Exception ex) {
            return SummarizationResult.failure(ex);
        }
    }
}
